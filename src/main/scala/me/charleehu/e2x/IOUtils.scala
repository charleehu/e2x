package me.charleehu.e2x
import java.io.InputStream
import java.io.FileInputStream
import scala.xml.Node
import java.nio.channels.Channels
import java.io.FileOutputStream
import java.io.File
import java.io.Writer
import scala.xml.PrettyPrinter

object IOUtils {
    def readFile(path: String)(deal: InputStream => Any) = {
        def input = new FileInputStream(path)
        try {
            deal(input)
        }
        finally {
            input.close
        }
    }
    
    def writeXML(path: String, name: String)(deal: Writer => Node) {
        val writer = Channels.newWriter(new FileOutputStream(new File(path, name)).getChannel(), "UTF-8")
        try {
            writer.write("<?xml version='1.0' encoding='UTF-8'?>\n")
            writer.write(new PrettyPrinter(80, 2).format(deal(writer)))
        } finally {
            writer.close
        }
    }
}