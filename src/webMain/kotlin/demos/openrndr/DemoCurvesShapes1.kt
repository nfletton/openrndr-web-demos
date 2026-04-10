package demos.openrndr

import demos.*
import offset.offset
import org.openrndr.application
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

                // Linear Segment: start point, end point
                drawer.isolated {
                    val seg = Segment2D(
                        cell.position(0.1, 0.2), cell.position(0.9, 0.8)
                    )
                    drawer.segment(seg)
                }

                cell = grid[0][1]
                drawBorder(drawer, cell)
                // Quadratic Segment: start point, control point, end point
                drawer.isolated {
                    val seg = Segment2D(
                        cell.position(0.1, 0.2), cell.position(0.9, 0.1), cell.position(0.9, 0.8)
                    )
                    drawer.segment(seg)
                }
                cell = grid[0][2]
                drawBorder(drawer, cell)
                // Cubic Segment: start point, control point, control point, end point
                drawer.isolated {
                    val seg = Segment2D(
                        cell.position(0.1, 0.2),
                        cell.position(0.2, 0.8),
                        cell.position(0.8, 0.2),
                        cell.position(0.9, 0.8)
                    )
                    drawer.segment(seg)
                }

                /* Segment properties */
                cell = grid[1][0]
                drawBorder(drawer, cell)
                // Position along a segment
                drawer.isolated {
                    val seg = Segment2D(
                        cell.position(0.1, 0.2), cell.position(0.9, 0.8)
                    )
                    drawer.segment(seg)
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    // Position on a segment
                    drawer.circle(seg.position(0.7), POINT_RADIUS)
                }

                cell = grid[1][1]
                drawBorder(drawer, cell)
                // Normal to a point on a segment
                drawer.isolated {
                    val seg = Segment2D(
                        cell.position(0.1, 0.2), cell.position(0.9, 0.1), cell.position(0.9, 0.8)
                    )
                    drawer.segment(seg)
                    drawer.stroke = LINE_COLOR2
                    val segPoint = seg.position(0.7)
                    drawer.segment(Segment2D(segPoint, segPoint - seg.normal(0.7) * 30.0))
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    drawer.circle(seg.position(0.7), POINT_RADIUS)
                }

                cell = grid[1][2]
                drawBorder(drawer, cell)
                // Equidistant positions along a segment
                drawer.isolated {
                    val seg = Segment2D(
                        cell.position(0.1, 0.2),
                        cell.position(0.2, 0.8),
                        cell.position(0.8, 0.2),
                        cell.position(0.9, 0.8)
                    )
                    drawer.segment(seg)
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    drawer.circles(seg.equidistantPositions(10).map { Circle(it, POINT_RADIUS) })
                }

                cell = grid[2][0]
                drawBorder(drawer, cell)
                // Nearest point on a segment to a given point
                drawer.isolated {
                    val seg = Segment2D(
                        cell.position(0.1, 0.2),
                        cell.position(0.2, 0.8),
                        cell.position(0.8, 0.2),
                        cell.position(0.7, 0.9)
                    )
                    drawer.segment(seg)
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    val point = cell.position(0.9, 0.5)
                    drawer.circle(point, POINT_RADIUS)
                    drawer.circle(seg.nearest(point).position, POINT_RADIUS)
                }

                cell = grid[2][1]
                drawBorder(drawer, cell)
                // Offset a segment by a given distance
                drawer.isolated {
                    val offset = cell.width / 12.0
                    val seg = Segment2D(
                        cell.position(0.05, 0.25),
                        cell.position(0.2, 0.8),
                        cell.position(0.8, 0.2),
                        cell.position(0.85, 0.85)
                    )
                    drawer.segment(seg)
                    drawer.stroke = LINE_COLOR2
                    drawer.segments(seg.offset(offset))
                }

                cell = grid[2][2]
                drawBorder(drawer, cell)
                // Subsegment of a segment
                drawer.isolated {
                    val seg = Segment2D(
                        cell.position(0.1, 0.25),
                        cell.position(0.2, 0.8),
                        cell.position(0.8, 0.2),
                        cell.position(0.9, 0.85)
                    )
                    drawer.segment(seg)
                    drawer.stroke = LINE_COLOR2
                    drawer.segment(seg.sub(0.2, 0.65))
                }

                cell = grid[3][0]
                drawBorder(drawer, cell)
                // Contour builder
                drawer.isolated {
                    val contour = contour {
                        moveTo(cell.position(0.1, 0.2))
                        lineTo(cell.position(0.2, 0.8))
                        lineTo(cell.position(0.3, 0.6))
                        lineTo(cell.position(0.7, 0.9))
                        lineTo(cell.position(0.9, 0.2))
                        lineTo(cell.position(0.5, 0.4))
                        close()
                    }
                    drawer.stroke = null
                    drawer.fill = FILL_COLOR
                    drawer.contour(contour)
                }

                cell = grid[3][1]
                drawBorder(drawer, cell)
                // Contour from points
                drawer.isolated {
                    val start = cell.position(0.1, 0.6)
                    val stepX = cell.width / 24.0
                    val stepY = cell.height / 45.0
                    val points = List(20) {
                        start + Vector2(stepX * it, sin(it * 1.0) * it * stepY)
                    }
                    val jaggedContour = ShapeContour.fromPoints(points, closed = false)
                    val smoothContour = hobbyCurve(points, closed = false)
                    drawer.contour(jaggedContour)
                    drawer.translate(0.0, -cell.height / 6)
                    drawer.stroke = LINE_COLOR2
                    drawer.contour(smoothContour)

                }

                cell = grid[3][2]
                drawBorder(drawer, cell)
                // Contour properties animated
                drawer.isolated {
                    val radius = min(cell.height * 0.3, cell.width / 7.5)
                    val pointRadius = radius * 0.15
                    val point = Circle(
                        cell.corner.x + cell.width * 0.18, cell.center.y, radius
                    ).contour.position((seconds * 0.1) % 1.0)
                    val points0 = Circle(cell.center, radius).contour.equidistantPositions(pointCount = max(1.0, (cell.height / 8)).toInt())
                    val points1 =
                        Circle(cell.corner.x + cell.width * 0.82, cell.center.y, radius).contour.equidistantPositions(
                            max(1.0, (cos(seconds) * 10.0 + 20.0)).toInt()
                        )

                    drawer.stroke = null
                    drawer.fill = FILL_COLOR
                    drawer.circle(point, pointRadius)
                    drawer.circles(points0, pointRadius)
                    drawer.circles(points1, pointRadius)
                }
            }
        }
    }
}