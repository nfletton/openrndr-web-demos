package demos.openrndr

import demos.*
import org.openrndr.application
import org.openrndr.color.ColorHSLa
import org.openrndr.color.ColorHSVa
import org.openrndr.color.ColorXSLa
import org.openrndr.color.ColorXSVa
import org.openrndr.draw.Drawer
import org.openrndr.draw.isolated
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle

fun DemoColorModels() {
    application {
        class HsvColorDemo(val cell: Rectangle) : CellDemo {
            override fun draw(drawer: Drawer) {
                drawer.isolated {
                    val stepsX = 32
                    val stepsY = 8
                    val sampleWidth = cell.width / stepsX
                    val sampleHeight = cell.height / stepsY

                    drawer.stroke = null
                    // -- draw hsv swatches
                    for (j in 0..<stepsY) {
                        for (i in 0..<stepsX) {
                            drawer.fill = ColorHSVa(360 * (i / 31.0), 0.7, 0.125 + j / 8.0).toRGBa()
                            drawer.rectangle(
                                cell.corner + Vector2(sampleWidth * i, sampleHeight * j), sampleWidth, sampleHeight
                            )
                        }
                    }
                }
                drawBorder(drawer, cell)
            }
        }

        class HslColorDemo(val cell: Rectangle) : CellDemo {
            override fun draw(drawer: Drawer) {
                drawer.isolated {
                    val stepsX = 32
                    val stepsY = 8
                    val sampleWidth = cell.width / stepsX
                    val sampleHeight = cell.height / stepsY

                    drawer.stroke = null
                    // -- draw hsl swatches
                    for (j in 0..<stepsY) {
                        for (i in 0..<stepsX) {
                            drawer.fill = ColorHSLa(360 * (i / 31.0), 0.7, 0.125 + j / 9.0).toRGBa()
                            drawer.rectangle(
                                cell.corner + Vector2(sampleWidth * i, sampleHeight * j), sampleWidth, sampleHeight
                            )
                        }
                    }
                }
                drawBorder(drawer, cell)
            }
        }

        class XsvColorDemo(val cell: Rectangle) : CellDemo {
            override fun draw(drawer: Drawer) {
                drawer.isolated {
                    val stepsX = 32
                    val stepsY = 8
                    val sampleWidth = cell.width / stepsX
                    val sampleHeight = cell.height / stepsY

                    drawer.stroke = null
                    // -- draw hsl swatches
                    for (j in 0..<stepsY) {
                        for (i in 0..<stepsX) {
                            drawer.fill = ColorXSVa(360 * (i / 31.0), 0.7, 0.125 + j / 8.0).toRGBa()
                            drawer.rectangle(
                                cell.corner + Vector2(sampleWidth * i, sampleHeight * j), sampleWidth, sampleHeight
                            )
                        }
                    }
                }
                drawBorder(drawer, cell)
            }
        }

        class XslColorDemo(val cell: Rectangle) : CellDemo {
            override fun draw(drawer: Drawer) {
                drawer.isolated {
                    val stepsX = 32
                    val stepsY = 8
                    val sampleWidth = cell.width / stepsX
                    val sampleHeight = cell.height / stepsY

                    drawer.stroke = null
                    // -- draw hsl swatches
                    for (j in 0..<stepsY) {
                        for (i in 0..<stepsX) {
                            drawer.fill = ColorXSLa(360 * (i / 31.0), 0.7, 0.125 + j / 9.0).toRGBa()
                            drawer.rectangle(
                                cell.corner + Vector2(sampleWidth * i, sampleHeight * j), sampleWidth, sampleHeight
                            )
                        }
                    }
                }
                drawBorder(drawer, cell)
            }
        }

        program {
            val columns = 1
            val rows = 4
            val margin = GRID_MARGIN
            val gutter = GRID_GUTTER * 4

            fun setupDemos(): List<CellDemo> {
                val grid = drawer.bounds.grid(
                    columns, rows, margin, margin, gutter, gutter
                )
                return listOf(
                    HsvColorDemo(grid[0][0]),
                    HslColorDemo(grid[1][0]),
                    XsvColorDemo(grid[2][0]),
                    XslColorDemo(grid[3][0]),
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

                /*
                                val cellWidth = grid[0][0].width
                                val cellHeight = grid[0][0].height

                                drawer.clear(BACKGROUND_COLOR)

                                *//* COLORS MODELS *//*

                drawer.stroke = null
                val left = grid[0][0].x
                var top = grid[0][0].y
                val stepsY = 8
                val stepsX = 32
                val width = cellWidth / stepsX
                val height = cellHeight / stepsY



                // -- draw xsv (Kuler) swatches
                top = grid[2][0].y
                for (j in 0..<stepsY) {
                    for (i in 0..<stepsX) {
                        drawer.fill = ColorXSVa(360 * (i / 31.0), 0.7, 0.125 + j / 8.0).toRGBa()
                        drawer.rectangle(left + width * i, top + height * j, width, height)
                    }
                }

                // -- draw xsl (Kuler) swatches
                top = grid[3][0].y
                for (j in 0..<stepsY) {
                    for (i in 0..<stepsX) {
                        drawer.fill =
                            ColorXSLa(360 * (i / 31.0), 0.7, 0.125 + j / 9.0, 1.0).toRGBa()
                        drawer.rectangle(left + width * i, top + height * j, width, height)
                    }
                }
*/
            }
        }
    }
}