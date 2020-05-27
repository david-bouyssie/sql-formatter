package com.github.takayahilton.sqlformatter.languages

import com.github.takayahilton.sqlformatter.core.{DialectConfig, FormatConfig, Formatter, Params, Tokenizer}
import FormatConfig.DEFAULT_INDENT

abstract class AbstractFormatter {
  def dialectConfig: DialectConfig

  /**
    * Formats DB2 query to make it easier to read
    *
    * @param query query string
    * @param cfg   FormatConfig
    * @return formatted string
    */
  def format(query: String, cfg: FormatConfig): String = {
    val tokenizer = new Tokenizer(dialectConfig)
    new Formatter(cfg, tokenizer).format(query)
  }

  def format[A](query: String, indent: String, params: Seq[A]): String = { //(implicit ev: SqlParamable[A])
    val strParams = params.map(_.toString)
    format(
      query,
      FormatConfig(indent = indent, params = Params.IndexedParams(strParams))
    )
  }

  def format[A](query: String, params: Seq[A]): String =
    format(query, DEFAULT_INDENT, params)

  def format[A](query: String, indent: String, params: Map[String, A]): String = {// (implicit ev: SqlParamable[A])
    val strParams = params.mapValues(_.toString)
    format(
      query,
      FormatConfig(indent = indent, params = Params.NamedParams(strParams))
    )
  }

  def format[A](query: String, params: Map[String, A]): String =
    format(query, DEFAULT_INDENT, params)

  def format(query: String, indent: String): String =
    format(query, FormatConfig(indent = indent, params = Params.EmptyParams))

  def format(query: String): String = format(query, DEFAULT_INDENT)
}
