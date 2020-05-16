## Promethus: A Time-Series DB with KEY-Value pairs uses in-memory db.

#### <ins> Intension</ins>: Visualize metrics in Promethus using scala & Kamon(lib)

#### Lib Used: Kamon (https://kamon.io/docs/latest/guides/frameworks/elementary-akka-setup)

#### Kamon will publish the metrics for scraping (localhost:9095)

#### Make sure Promethus is up and running and change the Promethus.yml file with adding below code at the end

``` 
- job_name: 'demo_app'
  scrape_interval: 1s
  metrics_path: ''
  static_configs:
  - targets: ['localhost:9095']
    
```
    
#### By doing above promethus will scrap metrics from localhost:9095
#### Clone the code and run the below API:
    
```
    curl --location --request GET 'http://localhost:9100/push/metrics' \
--header 'Content-Type: application/json'
    
```   
####  Hit the Url : localhost:9095 you should be able to see the metrics(search `DATA_INGESTED`) you should be able to see the count.
         ###### Hit the API multiple times and wait for 1 min atleast.

#### Open Promethus execute Query DATA_INGESTED_total you should see the metrics with count.

<ins> 80% Task Done Here </ins>



#### For better visualization we use Garafana, for connecting grafana with promethus add data source as promethus in Grafana also give url of promethus, then you should be able to see the metrics graph there.
#### For More info: Refer Documentation https://kamon.io/docs/latest/guides/frameworks/elementary-akka-setup
    
