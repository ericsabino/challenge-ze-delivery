FROM mongo

COPY pdvs.json /tmp/pdvs.json
CMD mongoimport -u "userdb" -p "pass123" --authenticationDatabase "admin"  --host mongodb --db zedelivery --collection parceiroze --type json --file /tmp/pdvs.json --jsonArray