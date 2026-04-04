package data

import SketchData
import demos.orxeasing.DemoEasings01

internal val sketchesORXEASING = listOf(
    SketchData(
        navTitle = "Easing Types",
        title = "ORX Easing",
        function = ::DemoEasings01,
        funcName = "DemoEasings01",
        pkg = Package.ORXEASING,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-easing",
        status = SketchStatus.PARTIAL,
        statusMessage = """
            Sketch dynamic behaviour functions fully. However, text labelling is 
            not implemented in WebGL yet and therefore the output does not match
            the JVM version of the demo."""
    ),
)
