/**
 * $Id$
 * Copyright 2012-2014 Oak Pacific Interactive. All rights reserved.
 */
package me.charleehu.e2x
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.WorkbookFactory
import scala.collection.immutable.Vector
import java.nio.channels.Channel
import java.nio.channels.Channels
import java.io.FileOutputStream
import java.io.File
import scala.xml.PrettyPrinter
import scala.xml.Elem
import scala.xml.Null
import scala.xml.TopScope
import scala.xml.Text

/**
 * @author <a href="mailto:xiaowei.hu@renren-inc.com">Xiaowei Hu</a>
 * @version 1.0 2012-12-17 下午07:07:07
 * @since 1.0
 */
object ExcelParser {
    def parse(path: String) = {
        IOUtils.readFile(path) { input =>
            val sheet = WorkbookFactory.create(input).getSheetAt(0)

            val headRow = sheet.getRow(0)
            val colName = (headRow.getFirstCellNum() until headRow.getLastCellNum()).map { index =>
                headRow.getCell(index).getStringCellValue()
            }

            val data = (sheet.getFirstRowNum() + 1 until sheet.getLastRowNum()).map { index =>
                val row = sheet.getRow(index)
                (row.getFirstCellNum() until row.getLastCellNum()).map { index =>
                    val cell = row.getCell(index)

                    var ret = ""
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING)
                        ret = cell.getStringCellValue()
                    }
                    ret
                }
            }

            (colName, data)
        }.asInstanceOf[(Vector[String], Vector[Vector[String]])]
    }

    def writeXML(path: String, name: String, data: (Vector[String], Vector[Vector[String]])) {
        IOUtils.writeXML(path, name) { writer =>
            <DICT name={ name }>
                {
                    data._2.map { dt =>
                        <TR>
                            {
                                dt.zipWithIndex.map {
                                    case (e, i) =>
                                        Elem(null, data._1(i), Null, TopScope, Text(e))
                                }
                            }
                        </TR>
                    }
                }
            </DICT>
        }

    }
}