const sqlite3 = require("sqlite3").verbose();

const db = new sqlite3.Database("./db/data.sqlite3", (err) => {
    if (err) {
        console.error("Error creating SQLite database:", err.message);
    } else {
        console.log("SQLite database file created successfully.");
    }
});

// Close the database connection
db.close((err) => {
    if (err) {
        console.error("Error closing SQLite database connection:", err.message);
    } else {
        console.log("SQLite database connection closed.");
    }
});