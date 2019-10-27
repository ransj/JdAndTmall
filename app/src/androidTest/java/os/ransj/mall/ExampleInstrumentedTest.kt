package os.ransj.mall

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2

import org.junit.Test
import org.junit.runner.RunWith

import kotlin.random.Random

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun jd() {
        // Context of the app under test.
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val ctx = InstrumentationRegistry.getInstrumentation().context
        val uiDevice = UiDevice.getInstance(instrumentation)
        for (txt in jDESC) {
            uiDevice.findObject(By.textStartsWith(txt)).run {
                val origin = text.indexOf('（')+1
                val start = text.substring(text.indexOf('（')+1, text.indexOf('/', origin)).toInt()
                val num = text.substring(text.indexOf('/', origin)+1, text.indexOf('个')).toInt()
                for (i in start until num) {
                    uiDevice.findObject(By.textStartsWith(txt)).click()
                    waitForTime(2000)
                    uiDevice.swipeUp(ctx)
                    uiDevice.pressBack()
                    waitForTime(3000)
                    uiDevice.findObject(By.text("朕知道了")).click()
                    waitForTime(3000)
                }
            }
        }
    }

    @Test
    fun tmall() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val ctx = InstrumentationRegistry.getInstrumentation().context
        val uiDevice = UiDevice.getInstance(instrumentation)
        var count = 0
        while (count < 3) {
            count = 0
            count += uiDevice.findObjects(By.text("去进店")).takeActions(uiDevice, ctx)
            count += uiDevice.findObjects(By.text("去浏览")).takeActions(uiDevice, ctx)
            count += uiDevice.findObjects(By.text("去浏览")).takeActions(uiDevice, ctx)
        }
    }

    private fun waitForTime(duration : Long) {
        val start = System.currentTimeMillis()
        while (start + duration > System.currentTimeMillis()) {
            Thread.yield()
        }
    }

    private fun List<UiObject2>.takeActions(uiDevice : UiDevice, ctx : Context) : Int {
        return if (isNotEmpty()) {
            get(Random(System.currentTimeMillis()).nextInt(0, size)).click()
            waitForTime(2000)
            uiDevice.swipeUp(ctx)
            waitForTime(15000)
            uiDevice.swipeUp(ctx)
            waitForTime(1000)
            uiDevice.pressBack()
            waitForTime(2000)
            0
        } else {
            1
        }
    }

    private fun Context.screenWidth() : Int {
        return resources.displayMetrics.widthPixels
    }

    private fun Context.screenHeight() : Int {
        return resources.displayMetrics.heightPixels
    }

    private fun UiDevice.swipeUp(ctx : Context) {
        val x = ctx.screenWidth() / 2
        val y = ctx.screenHeight() / 2
        swipe(x, y, x, y /2 , 10)
    }


    private val jDESC = arrayOf(
        "逛逛好店",
        "精彩会场",
        "精选好物",
        "更多好玩互动",
        "看京品推荐"
    )

}
