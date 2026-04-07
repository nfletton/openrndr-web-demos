package demos

import org.openrndr.shape.Rectangle

fun spannedCell(firstCell: Rectangle, endCell: Rectangle): Rectangle {
    val dimensions = endCell.position(1.0, 1.0) - firstCell.corner
    return Rectangle(firstCell.corner, dimensions.x, dimensions.y)
}
