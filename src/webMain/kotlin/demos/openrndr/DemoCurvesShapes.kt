package demos.openrndr

import demos.*
import offset.offset
import org.openrndr.application
import org.openrndr.draw.isolated
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.shape.Circle
import org.openrndr.shape.Rectangle
import org.openrndr.shape.Segment2D


fun DemoCurvesShapes() {
    application {
        program {
            val columns = 3
            val rows = 6
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

                /* Segments */
                var cell = grid[0][0]
                drawBorder(drawer, cell)
                // Linear Segment: start point, end point
                var seg = Segment2D(
                    cell.position(0.1, 0.2),
                    cell.position(0.9, 0.8)
                )
                drawer.segment(seg)

                cell = grid[0][1]
                drawBorder(drawer, cell)
                // Quadratic Segment: start point, control point, end point
                seg = Segment2D(
                    cell.position(0.1, 0.2),
                    cell.position(0.9, 0.1),
                    cell.position(0.9, 0.8)
                )
                drawer.segment(seg)

                cell = grid[0][2]
                drawBorder(drawer, cell)
                // Cubic Segment: start point, control point, control point, end point
                seg = Segment2D(
                    cell.position(0.1, 0.2),
                    cell.position(0.2, 0.8),
                    cell.position(0.8, 0.2),
                    cell.position(0.9, 0.8)
                )
                drawer.segment(seg)

                /* Segment properties */
                cell = grid[1][0]
                drawBorder(drawer, cell)
                // Linear Segment: start point, end point
                seg = Segment2D(
                    cell.position(0.1, 0.2),
                    cell.position(0.9, 0.8)
                )
                drawer.segment(seg)
                drawer.isolated {
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    // Position on a segment
                    drawer.circle(seg.position(0.7), POINT_RADIUS)
                }

                cell = grid[1][1]
                drawBorder(drawer, cell)
                // Quadratic Segment: start point, control point, end point
                seg = Segment2D(
                    cell.position(0.1, 0.2),
                    cell.position(0.9, 0.1),
                    cell.position(0.9, 0.8)
                )
                drawer.segment(seg)
                drawer.isolated {
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    drawer.circle(seg.position(0.7), POINT_RADIUS)
                    drawer.stroke = LINE_COLOR2
                    drawer.segment(Segment2D(seg.position(0.7), seg.normal(0.7) * 20.0 + seg.position(0.7)))
                }

                cell = grid[1][2]
                drawBorder(drawer, cell)
                // Cubic Segment: start point, control point, control point, end point
                seg = Segment2D(
                    cell.position(0.1, 0.2),
                    cell.position(0.2, 0.8),
                    cell.position(0.8, 0.2),
                    cell.position(0.9, 0.8)
                )
                drawer.segment(seg)
                drawer.isolated {
                    // Equidistant positions
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    drawer.circles(seg.equidistantPositions(10).map { Circle(it, POINT_RADIUS) })
                }

                cell = grid[2][0]
                drawBorder(drawer, cell)
                // Cubic Segment: start point, control point, control point, end point
                seg = Segment2D(
                    cell.position(0.1, 0.2),
                    cell.position(0.2, 0.8),
                    cell.position(0.8, 0.2),
                    cell.position(0.7, 0.9)
                )
                drawer.segment(seg)
                drawer.isolated {
                    // Equidistant positions
                    drawer.stroke = null
                    drawer.fill = POINT_COLOR
                    val point = cell.position(0.9, 0.5)
                    drawer.circle(point, POINT_RADIUS)
                    drawer.circle(seg.nearest(point).position, POINT_RADIUS)
                }

                cell = grid[2][1]
                drawBorder(drawer, cell)
                // Cubic Segment: start point, control point, control point, end point
                seg = Segment2D(
                    cell.position(0.1, 0.25),
                    cell.position(0.2, 0.8),
                    cell.position(0.8, 0.2),
                    cell.position(0.9, 0.85)
                )
                drawer.segment(seg)
                drawer.isolated {
                    drawer.stroke = LINE_COLOR2
                    drawer.segments(seg.offset(20.0))
                }

                cell = grid[2][2]
                drawBorder(drawer, cell)
                // Cubic Segment: start point, control point, control point, end point
                seg = Segment2D(
                    cell.position(0.1, 0.25),
                    cell.position(0.2, 0.8),
                    cell.position(0.8, 0.2),
                    cell.position(0.9, 0.85)
                )
                drawer.segment(seg)
                drawer.isolated {
                }

                drawer.stroke = LINE_COLOR1
                drawer.strokeWeight = LINE_WIDTH


            }
        }
    }
}