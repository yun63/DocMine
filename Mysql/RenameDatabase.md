## 重命名数据库

```mysql
mysql -uusername -ppassword old_db -sNe 'show tables' | while read table; \
    do mysql -u username -ppassword -sNe "rename table old_db.$table to new_db.$table"; done
```
