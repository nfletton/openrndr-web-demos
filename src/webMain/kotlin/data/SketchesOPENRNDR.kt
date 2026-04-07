package data

import GUIDE_ROOT
import SketchData
import demos.openrndr.DemoBasicDraw
import demos.openrndr.DemoCurvesShapes
import demos.openrndr.DemoColor
import demos.openrndr.DemoColorModels

internal val sketchesOPENRNDR = listOf(
    SketchData(
        navTitle = "Drawing Basics",
        title = "Drawing circles, rectangles and lines",
        function = ::DemoBasicDraw,
        funcName = "DemoBasicDraw",
        pkg = Package.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/circlesRectanglesLines.html",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "Color Basics",
        title = "Core color functionality",
        function = ::DemoColor,
        funcName = "DemoColor",
        pkg = Package.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/color.html#color-operations",
        status = SketchStatus.PARTIAL,
        statusMessage = "Colors are not rendering correctly",
        statusLinks = listOf("https://github.com/openrndr/openrndr/issues/454"),
    ),
    SketchData(
        navTitle = "Color Models",
        title = "Alternative color models",
        function = ::DemoColorModels,
        funcName = "DemoColorModels",
        pkg = Package.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/color.html#alternative-color-models",
        status = SketchStatus.PARTIAL,
        statusMessage = "Colors are not rendering correctly",
        statusLinks = listOf("https://github.com/openrndr/openrndr/issues/454"),
    ),
    SketchData(
        navTitle = "Curves & Shapes",
        title = "Curves & Shape Basics",
        function = ::DemoCurvesShapes,
        funcName = "DemoCurvesShapes",
        pkg = Package.OPENRNDR,
        docLink = "${GUIDE_ROOT}drawing/curvesAndShapes.html",
        status = SketchStatus.GOOD,
//        statusMessage = "Colors are not rendering correctly",
//        statusLinks = listOf("https://github.com/openrndr/openrndr/issues/454"),
    ),
)
