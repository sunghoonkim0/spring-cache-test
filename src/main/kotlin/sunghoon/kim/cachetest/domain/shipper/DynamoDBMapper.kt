package sunghoon.kim.cachetest.domain.shipper

import org.springframework.data.repository.CrudRepository

interface DynamoDBMapper: CrudRepository<BookView, String> {
}