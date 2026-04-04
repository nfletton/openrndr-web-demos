
internal data class SketchData(
    val navTitle: String,
    val title: String,
    val function: () -> Unit,
    val funcName: String,
    val pkg: Package,
    val docLink: String,
    val status: SketchStatus = SketchStatus.BROKEN,
    val statusMessage:String = "",
    val statusLinks:List<String> = emptyList(),
    val userMessage: String = "",
) {

    val funcId: String by lazy {
        "$pkg-$funcName".lowercase()
    }

    val codeLink: String by lazy {
        "$EXAMPLES_ROOT/${pkg.toString().lowercase()}/$funcName.kt"
    }
}