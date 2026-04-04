package data

import SketchData
import demos.orxcomposition.DemoCompositionDrawer01
import demos.orxcomposition.DemoCompositionDrawer02
import demos.orxcomposition.DemoCompositionDrawer03
import demos.orxcomposition.DemoCompositionDrawer04

internal val sketchesORXCOMPOSITION = listOf(
    SketchData(
        navTitle = "Basic Composition",
        title = "Demonstrates basic composition features",
        function = ::DemoCompositionDrawer01,
        funcName = "DemoCompositionDrawer01",
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer01",
        status = SketchStatus.PARTIAL,
        statusMessage = "No SVG file save. orx-svg has Java dependencies",
    ),
    SketchData(
        navTitle = "ClipMode 1",
        title = "Using ClipMode.REVERSE_DIFFERENCE to clip shapes",
        function = ::DemoCompositionDrawer02,
        funcName = "DemoCompositionDrawer02",
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer02",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "ClipMode 2",
        title = "Using ClipMode.REVERSE_DIFFERENCE to clip 3 shapes",
        function = ::DemoCompositionDrawer03,
        funcName = "DemoCompositionDrawer03",
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer03",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "Mouse Interaction",
        title = "Demonstrates how to add content and clear an existing Composition",
        function = ::DemoCompositionDrawer04,
        funcName = "DemoCompositionDrawer04",
        pkg = Package.ORXCOMPOSITION,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-composition#democompositiondrawer04",
        status = SketchStatus.GOOD,
        userMessage = "Click and drag mouse button to add circles. Right-click to clear composition.",
    ),
)
