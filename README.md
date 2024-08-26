# crypto-trading-signal
crypto-trading-signal


# running postgres container
```bash
docker run  --name tradingsignal -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=tradingsignal -p 5432:5432  postgres:14.4
```