package demos.openrndr

import demos.*
import offset.offset
import org.openrndr.Program
import org.openrndr.application
import org.openrndr.draw.Drawer
import org.openrndr.draw.isolated
import org.openrndr.extra.shapes.hobbycurve.hobbyCurve
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.math.Vector2
import org.openrndr.shape.*
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin


fun DemoCurvesShapes1() {
    application {
        class LinearSegmentDemo(val cell: Rectangle) : CellDemo {
            val seg = Segment2D(
                cell.position(0.1, 0.2), cell.position(0.9, 0.8)
            )

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)

                drawer.stroke = LINE_COLOR1
                drawer.strokeWeight = LINE_WIDTH

                // Linear Segment: start point, end point
                drawer.isolated {
                    drawer.segment(seg)
                }
            }
        }

        class QuadraticSegmentDemo(val cell: Rectangle) : CellDemo {
            val seg = Segment2D(
                cell.position(0.1, 0.2), cell.position(0.9, 0.1), cell.position(0.9, 0.8)
            )

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                // Quadratic Segment: start point, control point, end point
                drawer.isolated {
                    drawer.segment(seg)
                }
            }
        }

        class CubicSegmentDemo(val cell: Rectangle) : CellDemo {
            val seg = Segment2D(
                cell.position(0.1, 0.2), cell.position(0.2, 0.8), cell.position(0.8, 0.2), cell.position(0.9, 0.8)
            )

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                // Cubic Segment: start point, control point, control point, end point
                drawer.isolated {
                    drawer.segment(seg)
                }
            }
        }

        class PositionOnSegmentDemo(val cell: Rectangle) : CellDemo {
            val seg = Segment2D(
                cell.position(0.1, 0.2), cell.position(0.9, 0.8)
            )
            val position = seg.position(0.7)

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                // Position along a segment
                drawer.isolated {
                    drawer.segment(seg)
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    // Position on a segment
                    drawer.circle(position, POINT_RADIUS)
                }

            }
        }

        class NormalToSegmentDemo(val cell: Rectangle) : CellDemo {
            val seg = Segment2D(
                cell.position(0.1, 0.2), cell.position(0.9, 0.1), cell.position(0.9, 0.8)
            )
            val segPoint = seg.position(0.7)
            val normalSegment = Segment2D(segPoint, segPoint - seg.normal(0.7) * 30.0)

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                // Normal to a point on a segment
                drawer.isolated {
                    drawer.segment(seg)
                    drawer.stroke = LINE_COLOR2
                    drawer.segment(normalSegment)
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    drawer.circle(seg.position(0.7), POINT_RADIUS)
                }

            }
        }

        class EquidistantPositionsDemo(val cell: Rectangle) : CellDemo {
            val seg = Segment2D(
                cell.position(0.1, 0.2), cell.position(0.2, 0.8), cell.position(0.8, 0.2), cell.position(0.9, 0.8)
            )
            val points = seg.equidistantPositions(10).map { Circle(it, POINT_RADIUS) }

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                // Equidistant positions along a segment
                drawer.isolated {
                    drawer.segment(seg)
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    drawer.circles(points)
                }

            }
        }

        class NearestPointOnSegmentDemo(val cell: Rectangle) : CellDemo {
            val seg = Segment2D(
                cell.position(0.1, 0.2), cell.position(0.2, 0.8), cell.position(0.8, 0.2), cell.position(0.7, 0.9)
            )
            val point = cell.position(0.9, 0.5)
            val nearestPoint = seg.nearest(point).position
            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                // Nearest point on a segment to a given point
                drawer.isolated {
                    drawer.segment(seg)
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    drawer.circle(point, POINT_RADIUS)
                    drawer.circle(nearestPoint, POINT_RADIUS)
                }

            }
        }

        class OffsetSegmentDemo(val cell: Rectangle) : CellDemo {
            val offset = cell.width / 12.0
            val seg = Segment2D(
                cell.position(0.05, 0.25), cell.position(0.2, 0.8), cell.position(0.8, 0.2), cell.position(0.85, 0.85)
            )
            val offsetSeg = seg.offset(offset)

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                // Offset a segment by a given distance
                drawer.isolated {
                    drawer.segment(seg)
                    drawer.stroke = LINE_COLOR2
                    drawer.segments(offsetSeg)
                }

            }
        }

        class SubSegmentDemo(val cell: Rectangle) : CellDemo {
            val seg = Segment2D(
                cell.position(0.1, 0.25), cell.position(0.2, 0.8), cell.position(0.8, 0.2), cell.position(0.9, 0.85)
            )
            val subSeg = seg.sub(0.2, 0.65)

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                // Subsegment of a segment
                drawer.isolated {
                    drawer.segment(seg)
                    drawer.stroke = LINE_COLOR2
                    drawer.segment(subSeg)
                }

            }
        }

        class ContourBuilderDemo1(val cell: Rectangle) : CellDemo {
            val contour = contour {
                moveTo(cell.position(0.1, 0.2))
                lineTo(cell.position(0.2, 0.8))
                lineTo(cell.position(0.3, 0.6))
                lineTo(cell.position(0.7, 0.9))
                lineTo(cell.position(0.9, 0.2))
                lineTo(cell.position(0.5, 0.4))
                close()
            }

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                drawer.isolated {
                    drawer.stroke = null
                    drawer.fill = FILL_COLOR
                    drawer.contour(contour)
                }

            }
        }

        class ContourBuilderDemo2(val cell: Rectangle) : CellDemo {
            val start = cell.position(0.1, 0.6)
            val stepX = cell.width / 24.0
            val stepY = cell.height / 45.0
            val points = List(20) {
                start + Vector2(stepX * it, sin(it * 1.0) * it * stepY)
            }
            val jaggedContour = ShapeContour.fromPoints(points, closed = false)
            val smoothContour = hobbyCurve(points, closed = false)

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                drawer.isolated {
                    drawer.contour(jaggedContour)
                    drawer.translate(0.0, -cell.height / 6)
                    drawer.stroke = LINE_COLOR2
                    drawer.contour(smoothContour)
                }
            }
        }

        class ContourPropertiesDemo(val cell: Rectangle) : CellDemo {
            val radius = min(cell.height * 0.3, cell.width / 7.5)
            val pointRadius = radius * 0.15
            val contour1 = Circle(cell.corner.x + cell.width * 0.18, cell.center.y, radius).contour
            val contour2 = Circle(cell.center, radius).contour
            val contour3 = Circle(cell.corner.x + cell.width * 0.82, cell.center.y, radius).contour

            override fun draw(drawer: Drawer) {
                drawBorder(drawer, cell)
                drawer.isolated {
                    val point = contour1.position((program.seconds * 0.1) % 1.0)
                    val points0 = contour2.equidistantPositions(pointCount = max(1.0, (cell.height / 8)).toInt())
                    val points1 = contour3.equidistantPositions(max(1.0, (cos(program.seconds) * 10.0 + 20.0)).toInt())

                    drawer.stroke = null
                    drawer.fill = FILL_COLOR
                    drawer.circle(point, pointRadius)
                    drawer.circles(points0, pointRadius)
                    drawer.circles(points1, pointRadius)
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
                    LinearSegmentDemo(grid[0][0]),
                    QuadraticSegmentDemo(grid[0][1]),
                    CubicSegmentDemo(grid[0][2]),
                    PositionOnSegmentDemo(grid[1][0]),
                    NormalToSegmentDemo(grid[1][1]),
                    EquidistantPositionsDemo(grid[1][2]),
                    NearestPointOnSegmentDemo(grid[2][0]),
                    OffsetSegmentDemo(grid[2][1]),
                    SubSegmentDemo(grid[2][2]),
                    ContourBuilderDemo1(grid[3][0]),
                    ContourBuilderDemo2(grid[3][1]),
                    ContourPropertiesDemo(grid[3][2])
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