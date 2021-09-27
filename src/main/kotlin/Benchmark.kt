import com.displee.cache.CacheLibrary
import com.runetopic.cache.store.Js5Store
import io.guthix.js5.Js5Cache
import io.guthix.js5.container.disk.Js5DiskStore
import java.nio.file.Path
import kotlin.system.measureTimeMillis

/**
 * @author Jordan Abraham
 */
const val size = 21
val path = "${System.getProperty("user.home")}/614/"

fun main() {
    runetopic()
    //guthix()
    displee()
}

fun runetopic() {
    var store: Js5Store?
    val openTime = measureTimeMillis {
        store = Js5Store(Path.of(path))
    }
    println("RuneTopic Open Took: $openTime milliseconds.")

    if (store == null) return

    var count = 0
    val time = measureTimeMillis {
        (0 until size).forEach { indexId ->
            store!!.index(indexId).getGroups().forEach { group ->
                group.getFiles().forEach { _ ->
                    count++
                }
            }
        }
    }
    println("RuneTopic Loaded $count different files and checked for them in $time milliseconds.")
}

fun guthix() {
    var store: Js5DiskStore?
    val openTime = measureTimeMillis {
        store = Js5DiskStore.open(Path.of(path))
    }
    println("Guthix Open Took: $openTime milliseconds.")

    var count = 0
    val time = measureTimeMillis {
        store!!.use {
            val cache = Js5Cache(it)
            (0 until size).forEach { indexId ->
                val archive = cache.readArchive(indexId)
                archive.values.forEach { settings ->
                    try {
                        val group = archive.readGroup(settings.id)
                        group.entries.forEach { entry ->
                            group[entry.key]?.let { count++ }
                        }
                    } catch (e: Exception) {

                    }
                }
            }
        }
    }
    println("Guthix Loaded $count different files and checked for them in $time milliseconds.")
}

fun displee() {
    var library: CacheLibrary?
    val openTime = measureTimeMillis {
        library = CacheLibrary(path)
    }
    println("Displee Open Took: $openTime milliseconds.")

    if (library == null) return

    var count = 0
    val time = measureTimeMillis {
        (0 until size).forEach { indexId ->
            val index = library!!.index(indexId)
            index.archives().forEach { archive ->
                archive.files().forEach { file ->
                    library!!.data(index.id, archive.id, file.id)?.let { count++ }
                }
            }
        }
    }
    println("Displee Loaded $count different files and checked for them in $time milliseconds.")
}