package data

import SketchData
import demos.orxmath.DemoLeastSquares01
import demos.orxmath.DemoLeastSquares02
import demos.orxmath.DemoLinearRange02
import demos.orxmath.DemoLinearRange03
import demos.orxmath.RbfInterpolation01
import demos.orxmath.RbfInterpolation02

internal val sketchesORXMATH = listOf(
    SketchData(
        navTitle = "Linear Range 2",
        title = "ORX Math Linear Range",
        function = ::DemoLinearRange02,
        funcName = "DemoLinearRange02",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#linearrangedemolinearrange02",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "Linear Range 3",
        title = "ORX Math Linear Range",
        function = ::DemoLinearRange03,
        funcName = "DemoLinearRange03",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#linearrangedemolinearrange03",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "Least Squares 1",
        title = "Least squares method to fit a regression line to noisy points",
        function = ::DemoLeastSquares01,
        funcName = "DemoLeastSquares01",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#matrixdemoleastsquares01",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "Least Squares 2",
        title = "Least squares method to fit a cubic bezier to noisy points",
        function = ::DemoLeastSquares02,
        funcName = "DemoLeastSquares02",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#matrixdemoleastsquares02",
        status = SketchStatus.GOOD,
    ),
    SketchData(
        navTitle = "RBF 1",
        title = "",
        function = ::RbfInterpolation01,
        funcName = "RbfInterpolation01",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#rbfrbfinterpolation01",
        status = SketchStatus.BROKEN,
    ),
    SketchData(
        navTitle = "RBF 2",
        title = "",
        function = ::RbfInterpolation02,
        funcName = "RbfInterpolation02",
        pkg = Package.ORXMATH,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-math#rbfrbfinterpolation02",
        status = SketchStatus.BROKEN,
        statusMessage = "Console reports: INVALID_OPERATION: uniform3fv: location is not from the associated program",
    ),
)
