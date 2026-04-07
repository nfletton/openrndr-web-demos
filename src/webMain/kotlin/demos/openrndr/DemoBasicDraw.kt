package demos.openrndr

import demos.spannedCell
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.LineCap
import org.openrndr.extra.shapes.primitives.grid
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle
import kotlin.random.Random


fun DemoBasicDraw() {
    application {
        program {
            val columns = 3
            val rows = 6
            val margin = 20.0

            fun createGrid() = drawer.bounds.grid(
                columns, rows, margin, margin, margin, margin
            )

            var grid: List<List<Rectangle>> = createGrid()

            window.sized.listen {
                grid = createGrid()
            }

            extend {
                drawer.clear(ColorRGBa.BLACK)

                /* CIRCLES */
                var cell = grid[0][0]
                val circleRadius = if (cell.width < cell.height) cell.width / 2.0 else cell.height / 2.0
                // -- draw a circle with pink fill and white stroke
                drawer.stroke = ColorRGBa.WHITE
                drawer.fill = ColorRGBa.PINK
                drawer.strokeWeight = 1.0
                drawer.circle(cell.center, circleRadius)

                cell = grid[0][1]
                // -- draw a circle without a fill, but with white stroke
                drawer.stroke = ColorRGBa.WHITE
                drawer.fill = null
                drawer.strokeWeight = 1.0
                drawer.circle(cell.center, circleRadius)

                cell = grid[0][2]
                // -- draw a circle with pink fill, but without a stroke
                drawer.stroke = null
                drawer.fill = ColorRGBa.PINK
                drawer.strokeWeight = 1.0
                drawer.circle(cell.center, circleRadius)

                /* RECTANGLES */
                // -- draw rectangle with pink fill and white stroke
                cell = grid[1][0]
                drawer.fill = ColorRGBa.PINK
                drawer.stroke = ColorRGBa.WHITE
                drawer.strokeWeight = 1.0
                drawer.rectangle(cell.corner, cell.width, cell.height)

                // -- draw rectangle without fill, but with white stroke
                cell = grid[1][1]
                drawer.fill = null
                drawer.stroke = ColorRGBa.WHITE
                drawer.strokeWeight = 1.0
                drawer.rectangle(cell.corner, cell.width, cell.height)

                // -- draw a rectangle with pink fill, but without stroke
                cell = grid[1][2]
                drawer.fill = ColorRGBa.PINK
                drawer.stroke = null
                drawer.strokeWeight = 1.0
                drawer.rectangle(cell.corner, cell.width, cell.height)

                /* LINES */
                // -- setup line appearance
                drawer.stroke = ColorRGBa.WHITE
                drawer.strokeWeight = 5.0
                drawer.lineCap = LineCap.ROUND

                cell = Rectangle(grid[2][0].corner, (cell.width * columns) + (margin * (columns - 1)), cell.height)
                drawer.lineSegment(
                    cell.position(0.1, 0.25),
                    cell.position(0.9, 0.25)
                )

                drawer.lineCap = LineCap.BUTT
                drawer.lineSegment(
                    cell.position(0.1, 0.5),
                    cell.position(0.9, 0.5)
                )

                drawer.lineCap = LineCap.SQUARE
                drawer.lineSegment(
                    cell.position(0.1, 0.75),
                    cell.position(0.9, 0.75)
                )

                /* LINE STRIP */
                // -- setup line appearance
                drawer.stroke = ColorRGBa.WHITE
                drawer.strokeWeight = 5.0
                drawer.lineCap = LineCap.ROUND

                cell = spannedCell(grid[3][0], grid[3][columns - 1])
                var points = listOf(
                    cell.position(0.1, 0.0),
                    cell.position(0.5, 0.9),
                    cell.position(0.9, 0.0),
                )
                drawer.lineStrip(points)

                /* LINE LOOP */
                drawer.lineCap = LineCap.BUTT
                drawer.strokeWeight = 2.0

                val shiftedPoints = points.map { it + Vector2(0.0, cell.height + margin) }
                drawer.lineLoop(shiftedPoints)

                /* POINTS */
                val random = Random(0)
                val nPoints = 2000
                cell = spannedCell(grid[5][0], grid[5][columns - 1])
                points = buildList {

                    repeat(nPoints) {
                        val x = random.nextDouble(cell.corner.x, cell.corner.x + cell.width)
                        val y = random.nextDouble(cell.corner.y, cell.corner.y + cell.height)
                        add(Vector2(x, y))
                    }
                }
                drawer.fill = ColorRGBa.WHITE
                drawer.points(points)
            }
        }
    }
}