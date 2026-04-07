package demos

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.isolated
import org.openrndr.shape.Rectangle

/*
--color-tri-1: #21d633;
--color-tri-2: #004f00;
--color-tri-3: #2965f9;
--color-tri-4: #1740a5;
--color-tri-5: #c9001a;
--color-tri-6: #d65151;
 */
val CELL_BORDER_COLOR = ColorRGBa.fromHex("#add6d4")
val POINT_COLOR = ColorRGBa.fromHex("#21d633")
val SEGMENT_COLOR = ColorRGBa.fromHex("#00ff00")
val LINE_COLOR1 = ColorRGBa.fromHex("#ffffff")
val LINE_COLOR2 = ColorRGBa.fromHex("#d65151")
val BACKGROUND_COLOR = ColorRGBa.fromHex("#0a0d14")

val LINE_WIDTH = 2.0
val LINE_WIDTH_THICK = 5.0
val POINT_RADIUS = 3.0

val GRID_MARGIN = 20.0
val GRID_GUTTER = 15.0

fun spannedCell(firstCell: Rectangle, endCell: Rectangle): Rectangle {
    val (x, y) = endCell.position(1.0, 1.0) - firstCell.corner
    return Rectangle(firstCell.corner, x, y)
}

fun drawBorder(drawer: Drawer, rectangle: Rectangle) {
    drawer.isolated {
        drawer.stroke = CELL_BORDER_COLOR
        drawer.fill = null
        drawer.strokeWeight = 0.1
        drawer.rectangle(rectangle)
    }
}
