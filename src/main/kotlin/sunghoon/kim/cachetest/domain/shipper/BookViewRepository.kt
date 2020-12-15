package sunghoon.kim.cachetest.domain.shipper

import org.springframework.cache.annotation.CacheEvict
import java.util.*

interface BookViewRepository {

    // Without Cache
    fun saveWithoutCache(bookView: BookView)

    // Check return
    fun saveWithCacheableWithoutReturn(bookView: BookView)

    fun saveWithCacheableWithReturn(bookView: BookView): BookView

    fun saveWithCachePutWithoutReturn(bookView: BookView)

    fun saveWithCachePutWithReturn(bookView: BookView): BookView

    // Check key
    fun saveWithCacheableDefaultKey(bookView: BookView): BookView

    fun saveWithCacheableCustomKey(bookView: BookView): BookView

    fun saveWithCachePutDefaultKey(bookView: BookView): BookView

    fun saveWithCachePutCustomKey(bookView: BookView): BookView

    fun saveWithCacheable(bookView: BookView): BookView

    fun saveMultiParameterWithCachePut(bookView: BookView, id: String): BookView

    // Cacheable
    fun findById(shipperViewId: String): Optional<BookView>

    fun isExist(shipperViewId: String): Boolean

    // Check evict
    fun deleteWithoutEvict(bookView: BookView)

    fun deleteWithEvictDefaultKey(bookView: BookView)

    fun deleteWithEvictCustomKey(bookView: BookView)
}