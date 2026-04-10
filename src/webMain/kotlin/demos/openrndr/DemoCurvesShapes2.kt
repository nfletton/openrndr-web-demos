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
import org.openrndr.shape.compound
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min


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
                    val radius1 = min(cell.height / 10, cell.width / 20)
                    val radius2 = min(cell.height / 5, cell.width / 10)
                    val c = Pulley(
                        Circle(Vector2.ZERO, radius1),
                        Circle(cell.width * 0.35, cell.height * 0.45, radius2),
                    ).contour
                    val cr = c.rectified()

                    // Go from 0.0 to 1.0 in two seconds
                    // slowing down at both ends
                    val t = cos(PI * (seconds % 2.0) / 2.0) * 0.5 + 0.5

                    drawer.isolated {
                        drawer.stroke = LINE_COLOR1
                        drawer.strokeWeight = LINE_WIDTH_THIN
                        drawer.fill = null
                        drawer.translate(cell.corner + Vector2(cell.width / 10, cell.height / 5))
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
                    drawer.stroke = LINE_COLOR1
                    drawer.strokeWeight = LINE_WIDTH_THIN
                    drawer.fill = FILL_COLOR

                    val radius = min(cell.height * 0.25, cell.width / 7)
                    val col1x = cell.position(0.2, 0.0).x
                    val col2x = cell.center.x
                    val col3x = cell.position(0.78, 0.0).x
                    val rowOffset = radius * 0.7
                    val row2y = cell.center.y
                    val row1y = row2y - rowOffset
                    val row3y = row2y + rowOffset

                    // -- shape union
                    val su = compound {
                        union {
                            shape(Circle(col1x, row1y, radius).shape)
                            shape(Circle(col1x, row3y, radius).shape)
                        }
                    }
                    drawer.shapes(su)

                    // -- shape difference
                    val sd = compound {
                        difference {
                            shape(Circle(col2x, row1y, radius).shape)
                            shape(Circle(col2x, row3y, radius).shape)
                        }
                    }
                    drawer.shapes(sd)

                    // -- shape intersection
                    val si = compound {
                        intersection {
                            shape(Circle(col3x, row1y, radius).shape)
                            shape(Circle(col3x, row3y, radius).shape)
                        }
                    }
                    drawer.shapes(si)
                }

                cell = grid[0][2]
                drawBorder(drawer, cell)
                // Contour builder
                drawer.isolated {
                    drawer.stroke = LINE_COLOR1
                    drawer.strokeWeight = LINE_WIDTH_THIN
                    drawer.fill = FILL_COLOR

                    val radius = min(cell.height * 0.9, cell.width * 0.9)
                    val offset = radius * 0.9
                    val xOffset = Vector2(offset, 0.0)
                    val yOffset = Vector2(0.0, offset)

                    val cross = compound {
                        union {
                            intersection {
                                shape(Circle(cell.center - xOffset, radius).shape)
                                shape(Circle(cell.center + xOffset, radius).shape)
                            }
                            intersection {
                                shape(Circle(cell.center - yOffset, radius).shape)
                                shape(Circle(cell.center + yOffset, radius).shape)
                            }
                        }
                    }
                    drawer.shapes(cross)
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