#this is 1st slide
---
#this is 2nd slide
---
@[2-3]
```yaml
apiVersion: v1
kind: Service
metadata:
  labels:
    run: neo4j
  name: neo4j
  namespace: argonauts
spec:
  ports:
  - port: 7474
    protocol: TCP
    targetPort: 7474
    name: rest
  - port: 7687
    protocol: TCP
    targetPort: 7687
    name: bolt
  selector:
    app: neo4j
  sessionAffinity: None
  type: ClusterIP
```
---
@[2-3]
```java
@RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users() {
        String bookmark;
        Driver driver = GraphDatabase.driver( neo4jUri, token );

        //Config
        try ( Session session = driver.session( AccessMode.WRITE ) )
        {
            try(Transaction tx = session.beginTransaction()) {

                tx.run( "CREATE (u:User {screen_name: 'A.P. Cojones ' }) return u" );
                tx.run( "CREATE (u:User {screen_name: 'Ajit ' }) return u" );
                tx.run( "CREATE (u:User {screen_name: 'Hamilton ' }) return u" );
                tx.success();
            }

            bookmark = session.lastBookmark();
        }
        return String.format( "User created [%s]", bookmark );

    }
```
---