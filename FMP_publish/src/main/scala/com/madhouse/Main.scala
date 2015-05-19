package com.madhouse
import java.sql.DriverManager
import java.sql.Connection

object Main {
    def main(args: Array[String]): Unit = {
        // connect to the database named "mysql" on the localhost
        val driver = "com.mysql.jdbc.Driver"
        val url = "jdbc:mysql://localhost/fmp"
        val username = "madcore"
        val password = "madcore"
        // there's probably a better way to do this
        var connection:Connection = null
        try {
            // make the connection
            Class.forName(driver)
            connection = DriverManager.getConnection(url, username, password)

            // create the statement, and run the select query
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT id, email FROM t_fmp_user")
            while ( resultSet.next() ) {
                val id = resultSet.getString("id")
                val email = resultSet.getString("email")
                println("id, email = " + id + ", " + email)
            }
        } catch {
            case e => e.printStackTrace
        }
        connection.close()
        println("Hello, world!")
    }
}
