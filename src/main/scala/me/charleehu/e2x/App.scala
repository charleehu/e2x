package me.charleehu.e2x
import java.io.File

/**
 * @author ${user.name}
 */
object App {

    def main(args: Array[String]) {
        if (args.length == 0) {
            println("#error# please input excel file name")
        }
        
        args.foreach { filePath =>
            val file = new File(filePath)
            if (file.exists()) {
                val (path, name) = (file.getParent(), file.getName().replaceAll("(.xls$)|(.xlsx$)", ".xml"))
                ExcelParser.writeXML(path, name, ExcelParser.parse(file.getAbsolutePath()))
                println("success=> " + path + name)
            } else {
                println("#error# file not found: " + file.getAbsolutePath())
            }
        }
        
    }

}
