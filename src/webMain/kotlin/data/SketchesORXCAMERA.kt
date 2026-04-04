package data

import SketchData
import demos.orxcamera.DemoCamera2D01
import demos.orxcamera.DemoCamera2D02

internal val sketchesORXCAMERA = listOf(
    SketchData(
        navTitle = "2D 1",
        title = "Demonstrates 2D mouse panning and zooming",
        function = ::DemoCamera2D01,
        funcName = "DemoCamera2D01",
        pkg = Package.ORXCAMERA,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-camera#democamera2d01",
        status = SketchStatus.BROKEN,
    ),
    SketchData(
        navTitle = "2D 2",
        title = "Demonstrates 2D mouse panning and zooming with a custom camera",
        function = ::DemoCamera2D02,
        funcName = "DemoCamera2D02",
        pkg = Package.ORXCAMERA,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-camera#democamera2d02",
        status = SketchStatus.BROKEN,
    ),
)
