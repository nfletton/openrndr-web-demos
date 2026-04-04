package data

import SketchData
import demos.orxcompositor.DemoAside01
import demos.orxcompositor.DemoCompositor01
import demos.orxcompositor.DemoCompositor02

internal val sketchesORXCOMPOSITOR = listOf(
    SketchData(
        navTitle = "Layer Reuse",
        title = "Demonstrates how to reuse a layer in the Compositor by using aside { }",
        function = ::DemoAside01,
        funcName = "DemoAside01",
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#demoaside01",
        status = SketchStatus.BROKEN,
        statusMessage = """Failed after merge of Wasm code into master.""",
    ),
    SketchData(
        navTitle = "Layered Blurs",
        title = "Demonstrates using 3 layers of moving items with a different amount of blur",
        function = ::DemoCompositor01,
        funcName = "DemoCompositor01",
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#democompositor01",
        status = SketchStatus.BROKEN,
        statusMessage = """
            Failed after merge of Wasm code into master.<br>Console reports: GL_INVALID_OPERATION: 
            glDrawArrays: Feedback loop formed between Framebuffer and active Texture.
            """,
    ),
    SketchData(
        navTitle = "BufferMultisample",
        title = "Demonstration of using BufferMultisample on a per layer basis.",
        function = ::DemoCompositor02,
        funcName = "DemoCompositor02",
        pkg = Package.ORXCOMPOSITOR,
        docLink = "https://github.com/openrndr/orx/tree/master/orx-compositor#democompositor02",
        status = SketchStatus.BROKEN,
    ),
)
