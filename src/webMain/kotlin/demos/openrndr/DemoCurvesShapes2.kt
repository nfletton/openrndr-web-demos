package demos.openrndr

import demos.*
import org.openrndr.application
import org.openrndr.draw.isolated
import org.openrndr.extra.shapes.primitives.Pulley
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.extra.shapes.rectify.rectified
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle
import org.openrndr.shape.Rectangle
import kotlin.math.PI
import kotlin.math.cos


fun DemoCurvesShapes2() {
    application {
        program {
            val columns = 3
            val rows = 4
            val margin = GRID_MARGIN
            val gutter = GRID_GUTTER

            fun createGrid() = drawer.bounds.grid(
                columns, rows, margin, margin, gutter, gutter
            )

            var grid: List<List<Rectangle>> = createGrid()

            window.sized.listen {
                grid = createGrid()
            }

            extend {
                drawer.clear(BACKGROUND_COLOR)

                drawer.stroke = LINE_COLOR1
                drawer.strokeWeight = LINE_WIDTH

                var cell = grid[0][0]
                drawBorder(drawer, cell)
                // Contour builder
                drawer.isolated {
                    val c = Pulley(
                        Circle(Vector2.ZERO, cell.height / 9),
                        Circle(cell.width * 0.35, cell.height * 0.45, cell.height / 5)
                    ).contour
                    val cr = c.rectified()

                    // Go from 0.0 to 1.0 in two seconds
                    // slowing down at both ends
                    val t = cos(PI * (seconds % 2.0) / 2.0) * 0.5 + 0.5

                    drawer.isolated {
                        drawer.stroke = LINE_COLOR1
                        drawer.strokeWeight = LINE_WIDTH_THIN
                        drawer.fill = null
                        drawer.translate(cell.corner + Vector2(cell.width / 15, cell.height / 5))
                        drawer.contour(c)

                        // Note how segment length affects the speed
                        drawer.stroke = null
                        drawer.fill = POINT_COLOR
                        drawer.circle(c.position(t), POINT_RADIUS)
                    }
                    drawer.isolated {
                        drawer.stroke = LINE_COLOR1
                        drawer.strokeWeight = LINE_WIDTH_THIN
                        drawer.fill = null
                        drawer.translate(cell.corner + Vector2(cell.width * 0.5, cell.height / 5))
                        drawer.contour(c)
                        // The rectified contour provides a smooth animation
                        drawer.stroke = null
                        drawer.fill = POINT_COLOR
                        drawer.circle(cr.position(t), POINT_RADIUS)
                    }
                }

                cell = grid[0][1]
                drawBorder(drawer, cell)
                // Contour builder
                drawer.isolated {
                    drawer.stroke = null
                    drawer.fill = FILL_COLOR
//                    drawer.contour(contour)
                }

                cell = grid[0][2]
                drawBorder(drawer, cell)
                // Contour builder
                drawer.isolated {
                    drawer.stroke = null
                    drawer.fill = FILL_COLOR
//                    drawer.contour(contour)
                }

                /*
                                cell = grid[5][0]
                                drawBorder(drawer, cell)
                                // Contour builder
                                drawer.isolated {
                                    drawer.stroke = null
                                    drawer.fill = FILL_COLOR
                //                    drawer.contour(contour)
                                }

                                cell = grid[5][1]
                                drawBorder(drawer, cell)
                                // Contour builder
                                drawer.isolated {
                                    drawer.stroke = null
                                    drawer.fill = FILL_COLOR
                //                    drawer.contour(contour)
                                }

                                cell = grid[5][2]
                                drawBorder(drawer, cell)
                                // Contour builder
                                drawer.isolated {
                                    drawer.stroke = null
                                    drawer.fill = FILL_COLOR
                //                    drawer.contour(contour)
                                }

                */
            }
        }
    }
}