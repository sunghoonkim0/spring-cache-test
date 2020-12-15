package sunghoon.kim.cachetest.domain.shipper

import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.util.*

@Component
@CacheConfig(cacheNames = ["sunghoon.kim"])
class BookViewRepositoryImpl(
        private val dbMapper: DynamoDBMapper
): BookViewRepository {

    override fun saveWithoutCache(bookView: BookView) {
        dbMapper.save(bookView)
    }

    @Cacheable
    override fun saveWithCacheableWithoutReturn(bookView: BookView) {
        dbMapper.save(bookView)
    }

    @CachePut
    override fun saveWithCachePutWithoutReturn(bookView: BookView) {
        dbMapper.save(bookView)
    }

    @Cacheable
    override fun saveWithCacheableWithReturn(bookView: BookView): BookView {
        return dbMapper.save(bookView)
    }

    @CachePut
    override fun saveWithCachePutWithReturn(bookView: BookView): BookView {
        return dbMapper.save(bookView)
    }

    @Cacheable
    override fun saveWithCacheableDefaultKey(bookView: BookView): BookView {
        return dbMapper.save(bookView)
    }

    @CachePut
    override fun saveWithCachePutDefaultKey(bookView: BookView): BookView {
        return dbMapper.save(bookView)
    }

    @Cacheable(key = "#bookView.id")
    override fun saveWithCacheableCustomKey(bookView: BookView): BookView {
        return dbMapper.save(bookView)
    }

    @CachePut(key = "#bookView.id")
    override fun saveWithCachePutCustomKey(bookView: BookView): BookView {
        return dbMapper.save(bookView)
    }

    override fun saveWithCacheable(bookView: BookView): BookView {
        return dbMapper.save(bookView)
    }

    @CachePut
    override fun saveMultiParameterWithCachePut(bookView: BookView, id: String): BookView {
        return dbMapper.save(bookView)
    }

    @Cacheable
    override fun findById(shipperViewId: String): Optional<BookView> {
        return dbMapper.findById(shipperViewId)
    }

    override fun isExist(shipperViewId: String): Boolean {
        return dbMapper.existsById(shipperViewId)
    }

    override fun deleteWithoutEvict(bookView: BookView) {
        dbMapper.delete(bookView)
    }

    @CacheEvict
    override fun deleteWithEvictDefaultKey(bookView: BookView) {
        dbMapper.delete(bookView)
    }

    @CacheEvict(key = "#bookView.id")
    override fun deleteWithEvictCustomKey(bookView: BookView) {
        dbMapper.delete(bookView)
    }
}