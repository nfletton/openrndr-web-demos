export function initUI(sketchJson, webTarget) {
    let links;
    let activeLink = null;
    let navHidden = false;
    let statusHidden = true;
    const navVisCookie = 'navHidden';
    const statusVisCookie = 'statusHidden';
    const navWidthCookie = 'navWidth';
    const defaultNavWidth = '240px';
    const statusFilterCookie = 'statusFilter';
    const defaultStatusFilter = ['GOOD', 'PARTIAL'];
    let navWidth = defaultNavWidth;
    let selectedStatuses = defaultStatusFilter;

    const nav = document.getElementById('sidebar');

    function getCookie(name, defaultValue = null) {
        const value = `; ${document.cookie}`;
        const parts = value.split(`; ${name}=`);
        if (parts.length === 2) {
            const val = parts.pop().split(';').shift();
            try {
                return JSON.parse(decodeURIComponent(val));
            } catch (e) {
                return val;
            }
        }
        return defaultValue;
    }

    function setCookie(name, value) {
        const cookieValue = typeof value === 'string' ? value : encodeURIComponent(JSON.stringify(value));
        document.cookie = `${name}=${cookieValue}; path=/; SameSite=Lax`;
    }

    function setNavGroupOpenState(targetGroup) {
        const groups = nav.querySelectorAll('nav details.group')
        groups.forEach(g => {
            const shouldOpen = g === targetGroup;
            g.open = shouldOpen;
            g.setAttribute('aria-expanded', String(shouldOpen));
        });
    }

    function setActiveNavItem(activeNavId) {
        const newActiveNav = nav.querySelector(`#${activeNavId}`);
        if (!newActiveNav) return;

        const currentActiveNav = nav.querySelector('nav a.active');
        if (currentActiveNav) {
            currentActiveNav.classList.remove('active');
            currentActiveNav.setAttribute('aria-current', 'false');
        }
        newActiveNav.classList.add('active');
        newActiveNav.setAttribute('aria-current', 'page');
        const group = newActiveNav.closest('nav details.group');
        if (group) setNavGroupOpenState(group);
        return newActiveNav
    }


    function stepActiveNavItem(delta) {
        if (!links.length) return;
        const idx = activeLink ? links.indexOf(activeLink) : -1;
        const targetIdx = (idx + delta) < 0 ? links.length - 1 : (idx + delta) % links.length;
        const nextActiveNavId = links[targetIdx].id;
        const url = new URL(window.location.href);
        url.searchParams.set('sketch', nextActiveNavId);
        window.location.href = url.toString();
    }

    function initNavLinks(sketchData) {
        const navContainer = document.createElement('div');
        navContainer.className = 'groups';
        navContainer.setAttribute('role', 'tree');

        let sketchIndex = 0
        Object.keys(sketchData).forEach(groupName => {
            const groupData = sketchData[groupName].filter(sketch => selectedStatuses.includes(sketch.status));
            if (groupData.length === 0) return;

            const details = document.createElement('details');
            details.className = 'group';
            details.setAttribute('role', 'treeitem');
            details.open = false;
            details.setAttribute('aria-expanded', 'false');

            const summary = document.createElement('summary');
            summary.textContent = groupName;
            details.appendChild(summary);

            const ul = document.createElement('ul');
            ul.id = groupName;
            ul.setAttribute('role', 'group');

            groupData.forEach((sketch) => {
                const li = document.createElement('li');
                const link = document.createElement('a');
                const url = new URL(window.location.href);
                url.searchParams.set('sketch', sketch['funcId']);
                link.href = url.toString();
                link.id = sketch['funcId'];
                link.textContent = sketch['navTitle'];
                link.dataset.sketchId = String(sketchIndex++);
                link.className = 'nav-link';

                li.appendChild(link);
                ul.appendChild(li);
            });

            details.appendChild(ul);
            navContainer.appendChild(details);
        });

        return navContainer;
    }

    function initStatusFilter(sketchData) {
        const filterContainer = document.getElementById('status-filter');
        if (!filterContainer) return;

        const statuses = ['GOOD', 'PARTIAL', 'BROKEN'];
        const labels = {
            'GOOD': 'Good',
            'PARTIAL': 'Partial',
            'BROKEN': 'Broken'
        };

        statuses.forEach(status => {
            const label = document.createElement('label');
            label.className = 'status-filter-item';

            const checkbox = document.createElement('input');
            checkbox.type = 'checkbox';
            checkbox.value = status;
            checkbox.checked = selectedStatuses.includes(status);

            checkbox.addEventListener('change', () => {
                if (checkbox.checked) {
                    selectedStatuses.push(status);
                } else {
                    selectedStatuses = selectedStatuses.filter(s => s !== status);
                }

                if (selectedStatuses.length === 0) {
                    selectedStatuses = ['GOOD'];
                    const goodCheckbox = filterContainer.querySelector('input[value="GOOD"]');
                    if (goodCheckbox) {
                        goodCheckbox.checked = true;
                    }
                }
                setCookie(statusFilterCookie, selectedStatuses);

                // Re-render nav links
                const oldGroups = nav.querySelector('.groups');
                if (oldGroups) {
                    nav.replaceChild(initNavLinks(sketchData), oldGroups);
                }
                links = Array.from(nav.querySelectorAll('a[href]'));

                // Re-select active item if it's still visible
                const urlParams = new URLSearchParams(window.location.search);
                const activeNavId = urlParams.get('sketch');
                if (activeNavId) {
                    setActiveNavItem(activeNavId);
                }
            });

            label.appendChild(checkbox);
            label.appendChild(document.createTextNode(labels[status]));
            filterContainer.appendChild(label);
        });
    }

    function getToggleBtnIcon() {
        return statusHidden ? '▲' : '▼';
    }

    function initNavStatus() {
        const newNavWidth = getCookie(navWidthCookie, defaultNavWidth);
        const newNavHidden = getCookie(navVisCookie, navHidden);
        const newStatusHidden = getCookie(statusVisCookie, statusHidden);
        const newStatusFilter = getCookie(statusFilterCookie, defaultStatusFilter);

        if (newNavWidth !== navWidth) {
            navWidth = newNavWidth;
            setCookie(navWidthCookie, navWidth);
        }

        if (newNavHidden !== navHidden) {
            navHidden = newNavHidden;
            setCookie(navVisCookie, navHidden);
        }

        if (newStatusHidden !== statusHidden) {
            statusHidden = newStatusHidden;
            setCookie(statusVisCookie, statusHidden);
        }

        if (JSON.stringify(newStatusFilter) !== JSON.stringify(selectedStatuses)) {
            selectedStatuses = Array.isArray(newStatusFilter) ? newStatusFilter : defaultStatusFilter;
            setCookie(statusFilterCookie, selectedStatuses);
        }

        nav.style.width = navWidth;
        nav.setAttribute('aria-hidden', navHidden);

        if (navHidden) {
            document.body.classList.add('nav-collapsed');
            nav.classList.remove('visible');
        } else {
            document.body.classList.remove('nav-collapsed');
            nav.classList.add('visible');
        }

        const statusInfo = document.getElementById('status-info');
        if (statusInfo) {
            statusInfo.setAttribute('aria-hidden', statusHidden);
            const toggleBtn = document.getElementById('toggleStatusInfo');
            if (toggleBtn) {
                toggleBtn.textContent = getToggleBtnIcon();
            }
        }
    }

    function init() {
        let sketchData = {};
        try {
            sketchData = JSON.parse(sketchJson)
        } catch (e) {
            console.error('Failed to parse sketch data', e);
        }

        const btnPrev = document.getElementById('navPrev');
        const btnNext = document.getElementById('navNext');
        const btnToggle = document.getElementById('toggleSidebar');

        initNavStatus()
        initStatusFilter(sketchData)
        nav.appendChild(initNavLinks(sketchData))

        links = Array.from(nav.querySelectorAll('a[href]'));

        const urlParams = new URLSearchParams(window.location.search);
        const activeNavId = urlParams.get('sketch');

        let sketch = null;
        if (activeNavId) {
            activeLink = setActiveNavItem(activeNavId);
            for (const group in sketchData) {
                sketch = sketchData[group].find(s => s.funcId === activeNavId);
                if (sketch) break;
            }
        }


        // Header nav buttons
        btnPrev?.addEventListener('click', () => stepActiveNavItem(-1));
        btnNext?.addEventListener('click', () => stepActiveNavItem(1));

        // Sidebar toggle
        btnToggle?.addEventListener('click', () => {
            navHidden = !navHidden;
            if (navHidden) {
                document.body.classList.add('nav-collapsed');
                nav.classList.remove('visible');
            } else {
                document.body.classList.remove('nav-collapsed');
                nav.classList.add('visible');
            }
            nav.setAttribute('aria-hidden', navHidden);
            btnToggle.title = navHidden ? 'Expand sidebar' : 'Collapse sidebar';
            btnToggle.setAttribute('aria-label', btnToggle.title);
            btnToggle.setAttribute('title', btnToggle.title);
            setCookie(navVisCookie, navHidden);
        });

        // Status info toggle
        const btnToggleStatus = document.getElementById('toggleStatusInfo');
        const statusInfo = document.getElementById('status-info');
        btnToggleStatus?.addEventListener('click', () => {
            statusHidden = !statusHidden;
            statusInfo?.setAttribute('aria-hidden', statusHidden);
            btnToggleStatus.textContent = getToggleBtnIcon();
            setCookie(statusVisCookie, statusHidden);
        });

        // web target buttons
        const btnJs = document.getElementById('btnJs');
        const btnWasm = document.getElementById('btnWasm');
        if (webTarget === 'js') {
            btnJs.classList.add('active');
            btnJs.disabled = true;
            btnWasm.disabled = false;
            btnWasm.addEventListener('click', () => {
                document.location.href = document.location.href.replace("/js/", "/wasm/");
            });
        } else {
            btnWasm.classList.add('active');
            btnJs.disabled = false;
            btnWasm.disabled = true;
            btnJs.addEventListener('click', () => {
                document.location.href = document.location.href.replace("/wasm/", "/js/");
            });
        }

        const debounce = (fn, wait = 150) => {
            let t;
            return (...args) => {
                clearTimeout(t);
                t = setTimeout(() => fn(...args), wait);
            };
        };

        const onResize = debounce((entry) => {
            const width = entry.target.style.width;
            if (width && width !== navWidth) {
                navWidth = width;
                setCookie(navWidthCookie, width)
            }
        }, 150);

        const resizeObserver = new ResizeObserver(entries => {
            for (const entry of entries) onResize(entry);
        });

        resizeObserver.observe(nav);

        const codeButton = document.getElementById('code-btn');
        if (sketch && sketch.codeLink && codeButton) {
            codeButton.disabled = false;
            codeButton.addEventListener('click', (event) => {
                event.preventDefault();
                window.open(sketch.codeLink, '_blank');
            })
        }

        const docButton = document.getElementById('doc-btn');
        if (sketch && sketch.docLink && docButton) {
            docButton.disabled = false;
            docButton.addEventListener('click', (event) => {
                event.preventDefault();
                window.open(sketch.docLink, '_blank');
            })
        }

        if (sketch && sketch.title) {
            const titleEl = document.getElementById('title');
            if (titleEl) {
                titleEl.textContent = sketch.title;
            }
        }

        if (sketch && sketch.userMessage) {
            const messageEl = document.getElementById('message');
            if (messageEl) {
                messageEl.textContent = sketch.userMessage;
            }
        }

        const statusInfoContent = document.querySelector('.status-info-content');
        if (sketch && statusInfoContent) {
            statusInfoContent.innerHTML = `
                <div style="margin-bottom: 4px;"><strong>Status:</strong> ${sketch.statusDescription}</div>
                ${sketch.statusMessage ? `<div>${sketch.statusMessage}</div>` : ''}
                ${sketch.statusLinks ? sketch.statusLinks.map(link => `<div><a href="${link}" target="_blank">${link}</a></div>`).join('') : ''}
            `;
        }
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
}