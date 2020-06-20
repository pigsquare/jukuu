import csv
import MySQLdb


db = MySQLdb.connect(host="127.0.0.1", user="star",
                     passwd="password", db="jukuu", charset="utf8")
with open("ecdict1.csv", "r", encoding="utf-8") as f:
    cursor = db.cursor()
    reader = csv.reader(f)
    for row in reader:
        print(row[0], row[1], row[2], row[3], row[10])
        sql = "INSERT INTO word(word,phonetic,definition,translation,exchange) VALUES (%s,%s,%s,%s,%s)"
        try:
            # 执行sql语句
            cursor.execute(sql, (row[0], row[1], row[2], row[3], row[10],))
            # 提交到数据库执行
            db.commit()
        except:
            # Rollback in case there is any error
            db.rollback()
    db.close()
