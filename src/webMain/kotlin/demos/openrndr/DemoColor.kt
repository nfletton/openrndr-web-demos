package demos.openrndr

import demos.*
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.color.mix
import org.openrndr.draw.Drawer
import org.openrndr.draw.isolated
import org.openrndr.extra.color.presets.DARK_SLATE_GRAY
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle


fun DemoColor() {
    application {
        class ColorOperations1(val cell: Rectangle) : CellDemo {
            override fun draw(drawer: Drawer) {
                drawer.isolated {
                    // shades
                    val baseColor = ColorRGBa.PINK
                    drawer.stroke = null
                    val steps = 16
                    val sampleWidth = cell.width / steps
                    val sampleHeight = cell.height / 2
                    // -- draw 16 darker shades of base color
                    for (i in 0..<steps) {
                        drawer.fill = baseColor.shade(i / (steps - 1.0))
                        drawer.rectangle(cell.corner + Vector2(sampleWidth * i, 0.0), sampleWidth, sampleHeight)
                    }
                    // -- draw 16 lighter shades of base color
                    for (i in 0..<steps) {
                        drawer.fill = baseColor.shade(1.0 + i / (steps - 1.0))
                        drawer.rectangle(
                            cell.corner + Vector2(sampleWidth * i, sampleHeight), sampleWidth, sampleHeight
                        )
                    }
                }
                drawBorder(drawer, cell)
            }

        }

        class ColorOperations2(val cell: Rectangle) : CellDemo {
            override fun draw(drawer: Drawer) {
                drawer.isolated {
                    drawer.fill = ColorRGBa.DARK_SLATE_GRAY
                    drawer.stroke = null
                    val baseColor = ColorRGBa.PINK
                    val steps = 16
                    val barHeight = cell.height * 2 / 3
                    val sampleWidth = cell.width / steps
                    drawer.rectangle(cell.corner, cell.width, barHeight)

                    // draw 16 pinks of stepped opacity
                    for (i in 0..<steps) {
                        drawer.fill = baseColor.opacify(i / 15.0)
                        drawer.rectangle(
                            cell.corner + Vector2(sampleWidth * i, cell.height * 1 / 3), sampleWidth, barHeight
                        )
                    }
                }
                drawBorder(drawer, cell)
            }

        }

        class ColorOperations3(val cell: Rectangle) : CellDemo {
            override fun draw(drawer: Drawer) {
                drawer.isolated {
                    drawer.stroke = null

                    // -- draw 16 color mixes
                    val leftColor = ColorRGBa.PINK
                    val rightColor = ColorRGBa.fromHex(0xFC3549)
                    val steps = 16
                    val sampleWidth = cell.width / steps
                    for (i in 0..<steps) {
                        drawer.fill = mix(leftColor, rightColor, i / 15.0)
                        drawer.rectangle(cell.corner + Vector2(sampleWidth * i, 0.0), sampleWidth, cell.height)
                    }
                }
                drawBorder(drawer, cell)
            }

        }

        program {
            val columns = 1
            val rows = 3
            val margin = GRID_MARGIN
            val gutter = GRID_GUTTER * 4

            fun setupDemos(): List<CellDemo> {
                val grid = drawer.bounds.grid(
                    columns, rows, margin, margin, gutter, gutter
                )
                return listOf(
                    ColorOperations1(grid[0][0]), ColorOperations2(grid[1][0]), ColorOperations3(grid[2][0])
                )
            }

            var demos: List<CellDemo> = setupDemos()

            window.sized.listen {
                demos = setupDemos()
            }

            extend {
                drawer.clear(BACKGROUND_COLOR)
                drawer.stroke = LINE_COLOR1
                drawer.strokeWeight = LINE_WIDTH

                demos.forEach { it.draw(drawer) }
            }
        }
    }
}