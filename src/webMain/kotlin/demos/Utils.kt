package demos

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Drawer
import org.openrndr.draw.isolated
import org.openrndr.shape.Rectangle

/*
Highlight colors:
https://colorpalette.pro/?color=%23ffc0cb&paletteType=com&paletteStyle=square&colorFormat=hex&effects=0%2C0%2C0%2C0

https://colorpalette.pro/?color=%23ffc0cb&paletteType=ana&paletteStyle=square&colorFormat=hex&effects=0%2C0%2C0%2C0
*/
val CELL_BORDER_COLOR = ColorRGBa.fromHex("#add6d4")
val POINT_COLOR = ColorRGBa.fromHex("#8aa8c3")
val LINE_COLOR1 = ColorRGBa.WHITE
val LINE_COLOR2 = ColorRGBa.fromHex("#804652")
val BACKGROUND_COLOR = ColorRGBa.fromHex("#0a0d14")
val FILL_COLOR = ColorRGBa.PINK

val LINE_WIDTH_THIN = 1.0
val LINE_WIDTH = 2.0
val LINE_WIDTH_THICK = 5.0
val POINT_RADIUS = 4.0

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
