import kotlinx.serialization.Serializable

internal enum class SketchStatus(val message: String) {
    HIDDEN("Hidden"),
    GOOD("Fully functional"),
    PARTIAL("Partial functionality"),
    BROKEN("Non-working"),
}

@Serializable
internal enum class Package(val displayName: String) {
    OPENRNDR("OPENRNDR"),
    ORXCAMERA("ORX Camera"),
    ORXCOMPOSITION("ORX Composition"),
    ORXCOMPOSITOR("ORX Compositor"),
    ORXEASING("ORX Easing"),
    ORXMATH("ORX Math"),
}

internal const val EXAMPLES_ROOT =
    "https://github.com/nfletton/openrndr-web-demos/blob/master/src/webMain/kotlin"
internal const val GUIDE_ROOT = "https://guide.openrndr.org/"
