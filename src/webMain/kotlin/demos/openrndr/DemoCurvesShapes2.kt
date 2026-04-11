package demos.openrndr

import demos.*
import offset.offset
import org.openrndr.Program
import org.openrndr.application
import org.openrndr.draw.Drawer
import org.openrndr.draw.LineJoin
import org.openrndr.draw.isolated
import org.openrndr.extra.shapes.primitives.Pulley
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.extra.shapes.rectify.rectified
import org.openrndr.math.Vector2
import org.openrndr.shape.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin


fun DemoCurvesShapes2() {
    application {
        class RectifiedContourDemo(val cell: Rectangle, val program: Program) : CellDemo {
            val radius1 = min(cell.height / 10, cell.width / 20)
            val radius2 = min(cell.height / 5, cell.width / 10)
            val c = Pulley(
                Circle(Vector2.ZERO, radius1),
                Circle(cell.width * 0.35, cell.height * 0.45, radius2),
            ).contour
            val cr = c.rectified()
            val offset1 = Vector2(cell.width / 10, cell.height / 5)
            val offset2 = Vector2(cell.width * 0.5, cell.height / 5)

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                drawer.isolated {
                    // Go from 0.0 to 1.0 in two seconds
                    // slowing down at both ends
                    val t = cos(PI * (program.seconds % 2.0) / 2.0) * 0.5 + 0.5

                    drawer.isolated {
                        drawer.stroke = LINE_COLOR1
                        drawer.strokeWeight = LINE_WIDTH_THIN
                        drawer.fill = null
                        drawer.translate(cell.corner + offset1)
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
                        drawer.translate(cell.corner + offset2)
                        drawer.contour(c)
                        // The rectified contour provides a smooth animation
                        drawer.stroke = null
                        drawer.fill = POINT_COLOR
                        drawer.circle(cr.position(t), POINT_RADIUS)
                    }
                }


            }
        }

        class SubContourDemo(val cell: Rectangle, val program: Program) : CellDemo {
            val radius = min(cell.height * 0.25, cell.width / 7)
            val col2x = cell.center.x
            val col1x = col2x - radius * 2
            val col3x = col2x + radius * 2
            val center = cell.center.y
            val contour1 = Circle(col1x, center, radius).contour
            val contour2 = Circle(col2x, center, radius).contour
            val contour3 = Circle(col3x, center, radius).contour

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                drawer.isolated {
                    drawer.stroke = LINE_COLOR1
                    drawer.strokeWeight = LINE_WIDTH_THIN
                    drawer.fill = FILL_COLOR

                    val sub0 = contour1.sub(0.0, 0.5 + 0.50 * sin(program.seconds))
                    drawer.contour(sub0)

                    val sub1 =
                        contour2.sub(program.seconds * 0.1, program.seconds * 0.1 + 0.1)
                    drawer.contour(sub1)

                    val sub2 =
                        contour3.sub(-program.seconds * 0.05, program.seconds * 0.05 + 0.1)
                    drawer.contour(sub2)
                }

            }
        }

        class OffsetSegmentDemo1(val cell: Rectangle, val program: Program) : CellDemo {
            val margin = min(cell.width, cell.height) * 0.26
            val width = cell.width - margin * 2
            val height = cell.height - margin * 2
            val offset = min(height, height) * 0.05
            val c = Rectangle(cell.corner + margin, width, height).contour.reversed

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                drawer.isolated {
                    drawer.stroke = LINE_COLOR1
                    drawer.strokeWeight = LINE_WIDTH_THIN
                    drawer.fill = null

                    val offsets = (1 until 10).map { i ->
                        c.offset(cos(program.seconds + 0.5) * i * offset, SegmentJoin.BEVEL)
                    }
                    drawer.contours(offsets)
                }
            }
        }

        class OffsetSegmentDemo2(val cell: Rectangle, val program: Program) : CellDemo {
            val offset = min(cell.width, cell.height) * 0.02

            val c = contour {
                moveTo(cell.center.x, cell.position(0.0, 0.2).y)
                curveTo(cell.position(0.25, 0.4), cell.position(0.75, 0.6), cell.position(0.5, 0.8))
            }

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                drawer.isolated {
                    drawer.stroke = LINE_COLOR1
                    drawer.strokeWeight = LINE_WIDTH_THIN
                    drawer.fill = null
                    drawer.lineJoin = LineJoin.ROUND

                    drawer.contour(c)
                    for (i in -7..7) {
                        val o = c.offset(i * offset * cos(program.seconds + 0.5))
                        drawer.contour(o)
                    }
                }
            }
        }

        class BooleanOpsDemo1(val cell: Rectangle, val program: Program) : CellDemo {
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

            // -- shape difference
            val sd = compound {
                difference {
                    shape(Circle(col2x, row1y, radius).shape)
                    shape(Circle(col2x, row3y, radius).shape)
                }
            }

            // -- shape intersection
            val si = compound {
                intersection {
                    shape(Circle(col3x, row1y, radius).shape)
                    shape(Circle(col3x, row3y, radius).shape)
                }
            }

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                drawer.isolated {
                    drawer.stroke = LINE_COLOR1
                    drawer.strokeWeight = LINE_WIDTH_THIN
                    drawer.fill = FILL_COLOR

                    drawer.shapes(su)
                    drawer.shapes(sd)
                    drawer.shapes(si)
                }
            }
        }

        class BooleanOpsDemo2(val cell: Rectangle, val program: Program) : CellDemo {
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

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                drawer.isolated {
                    drawer.stroke = LINE_COLOR1
                    drawer.strokeWeight = LINE_WIDTH_THIN
                    drawer.fill = FILL_COLOR

                    drawer.shapes(cross)
                }
            }
        }

        program {
            val columns = 3
            val rows = 4
            val margin = GRID_MARGIN
            val gutter = GRID_GUTTER

            fun setupDemos(): List<CellDemo> {
                val grid = drawer.bounds.grid(
                    columns, rows, margin, margin, gutter, gutter
                )
                return listOf(
                    RectifiedContourDemo(grid[0][0], this),
                    SubContourDemo(grid[0][1], this),
                    OffsetSegmentDemo1(grid[0][2], this),
                    OffsetSegmentDemo2(grid[1][0], this),
                    BooleanOpsDemo1(grid[1][1], this),
                    BooleanOpsDemo2(grid[1][2], this)
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