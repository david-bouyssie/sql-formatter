package com.github.takayahilton.sqlformatter.languages

import com.github.takayahilton.sqlformatter.core.DialectConfig

object N1qlFormatter {
  private val reservedWords = List(
    "ALL",
    "ALTER",
    "ANALYZE",
    "AND",
    "ANY",
    "ARRAY",
    "AS",
    "ASC",
    "BEGIN",
    "BETWEEN",
    "BINARY",
    "BOOLEAN",
    "BREAK",
    "BUCKET",
    "BUILD",
    "BY",
    "CALL",
    "CASE",
    "CAST",
    "CLUSTER",
    "COLLATE",
    "COLLECTION",
    "COMMIT",
    "CONNECT",
    "CONTINUE",
    "CORRELATE",
    "COVER",
    "CREATE",
    "DATABASE",
    "DATASET",
    "DATASTORE",
    "DECLARE",
    "DECREMENT",
    "DELETE",
    "DERIVED",
    "DESC",
    "DESCRIBE",
    "DISTINCT",
    "DO",
    "DROP",
    "EACH",
    "ELEMENT",
    "ELSE",
    "END",
    "EVERY",
    "EXCEPT",
    "EXCLUDE",
    "EXECUTE",
    "EXISTS",
    "EXPLAIN",
    "FALSE",
    "FETCH",
    "FIRST",
    "FLATTEN",
    "FOR",
    "FORCE",
    "FROM",
    "FUNCTION",
    "GRANT",
    "GROUP",
    "GSI",
    "HAVING",
    "IF",
    "IGNORE",
    "ILIKE",
    "IN",
    "INCLUDE",
    "INCREMENT",
    "INDEX",
    "INFER",
    "INLINE",
    "INNER",
    "INSERT",
    "INTERSECT",
    "INTO",
    "IS",
    "JOIN",
    "KEY",
    "KEYS",
    "KEYSPACE",
    "KNOWN",
    "LAST",
    "LEFT",
    "LET",
    "LETTING",
    "LIKE",
    "LIMIT",
    "LSM",
    "MAP",
    "MAPPING",
    "MATCHED",
    "MATERIALIZED",
    "MERGE",
    "MINUS",
    "MISSING",
    "NAMESPACE",
    "NEST",
    "NOT",
    "NULL",
    "NUMBER",
    "OBJECT",
    "OFFSET",
    "ON",
    "OPTION",
    "OR",
    "ORDER",
    "OUTER",
    "OVER",
    "PARSE",
    "PARTITION",
    "PASSWORD",
    "PATH",
    "POOL",
    "PREPARE",
    "PRIMARY",
    "PRIVATE",
    "PRIVILEGE",
    "PROCEDURE",
    "PUBLIC",
    "RAW",
    "REALM",
    "REDUCE",
    "RENAME",
    "RETURN",
    "RETURNING",
    "REVOKE",
    "RIGHT",
    "ROLE",
    "ROLLBACK",
    "SATISFIES",
    "SCHEMA",
    "SELECT",
    "SELF",
    "SEMI",
    "SET",
    "SHOW",
    "SOME",
    "START",
    "STATISTICS",
    "STRING",
    "SYSTEM",
    "THEN",
    "TO",
    "TRANSACTION",
    "TRIGGER",
    "TRUE",
    "TRUNCATE",
    "UNDER",
    "UNION",
    "UNIQUE",
    "UNKNOWN",
    "UNNEST",
    "UNSET",
    "UPDATE",
    "UPSERT",
    "USE",
    "USER",
    "USING",
    "VALIDATE",
    "VALUE",
    "VALUED",
    "VALUES",
    "VIA",
    "VIEW",
    "WHEN",
    "WHERE",
    "WHILE",
    "WITH",
    "WITHIN",
    "WORK",
    "XOR"
  )
  private val reservedToplevelWords = List(
    "DELETE FROM",
    "EXCEPT ALL",
    "EXCEPT",
    "EXPLAIN DELETE FROM",
    "EXPLAIN UPDATE",
    "EXPLAIN UPSERT",
    "FROM",
    "GROUP BY",
    "HAVING",
    "INFER",
    "INSERT INTO",
    "INTERSECT ALL",
    "INTERSECT",
    "LET",
    "LIMIT",
    "MERGE",
    "NEST",
    "ORDER BY",
    "PREPARE",
    "SELECT",
    "SET CURRENT SCHEMA",
    "SET SCHEMA",
    "SET",
    "UNION ALL",
    "UNION",
    "UNNEST",
    "UPDATE",
    "UPSERT",
    "USE KEYS",
    "VALUES",
    "WHERE"
  )
  private val reservedNewlineWords = List(
    "AND",
    "INNER JOIN",
    "JOIN",
    "LEFT JOIN",
    "LEFT OUTER JOIN",
    "OR",
    "OUTER JOIN",
    "RIGHT JOIN",
    "RIGHT OUTER JOIN",
    "XOR"
  )
}

class N1qlFormatter extends AbstractFormatter {
  import N1qlFormatter._

  val dialectConfig: DialectConfig =
    DialectConfig(
      reservedWords = reservedWords,
      reservedToplevelWords = reservedToplevelWords,
      reservedNewlineWords = reservedNewlineWords,
      stringTypes = List("\"\"", "''", "``"),
      openParens = List("(", "[", "{"),
      closeParens = List(")", "]", "}"),
      indexedPlaceholderTypes = Nil,
      namedPlaceholderTypes = List("$"),
      lineCommentTypes = List("#", "--"),
      specialWordChars = Nil
    )
}
