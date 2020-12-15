package sunghoon.kim.cachetest

import org.junit.jupiter.api.Assertions.*
import sunghoon.kim.cachetest.domain.shipper.BookView
import sunghoon.kim.cachetest.domain.shipper.BookViewRepositoryImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import java.util.*

@SpringBootTest
class SpringCacheTest {

    @Autowired
    lateinit var cacheManager: CacheManager

    // BookViewRepository
    // `- BookViewRepositoryImpl
    //     `- DynamoDBMapper

    @Autowired
    lateinit var shipperViewRepositoryImpl: BookViewRepositoryImpl

    private fun nextUUID(): String {
        return UUID.randomUUID().toString()
    }
    private fun getCacheById(id: String): Optional<BookView> {
        return Optional.ofNullable(cacheManager.getCache("sunghoon.kim")).map { c: Cache -> c.get(id, BookView::class.java) }
    }

    private fun getCacheById(id: BookView): Optional<BookView> {
        return Optional.ofNullable(cacheManager.getCache("sunghoon.kim")).map { c: Cache -> c.get(id, BookView::class.java) }
    }

    private fun isCacheExistById(id: String): Boolean {
        return cacheManager.getCache("sunghoon.kim")!!.get(id) != null
    }

    private fun isCacheExistById(id: BookView): Boolean {
        return cacheManager.getCache("sunghoon.kim")!!.get(id) != null
    }

    // Check relation between 'cache data' and 'method return'
    @Test
    fun cacheableWithVoidMethodHasNullCache() {
        // Given
        val bookView = BookView(id = nextUUID(), name = "Kotlin_in_action")

        // When
        shipperViewRepositoryImpl.saveWithCacheableWithoutReturn(bookView)

        // Then
        assertTrue(isCacheExistById(bookView))
        assertFalse(getCacheById(bookView).isPresent)
    }

    @Test
    fun cachePutWithVoidMethodHasNullCache() {
        // Given
        val bookView = BookView(id = nextUUID(), name = "Kotlin_in_action")

        // When
        shipperViewRepositoryImpl.saveWithCacheableWithoutReturn(bookView)

        // Then
        assertTrue(isCacheExistById(bookView))
        assertFalse(getCacheById(bookView).isPresent)
    }

    @Test
    fun cacheableMethodReturnValueIsCacheData() {
        // Given
        val bookView = BookView(id = nextUUID(), name = "Doing_more_with_java")

        // When
        shipperViewRepositoryImpl.saveWithCacheableWithReturn(bookView)

        // Then
        assertEquals(true, isCacheExistById(bookView))
        assertEquals(bookView.id, getCacheById(bookView).get().id)
        assertEquals(bookView.name, getCacheById(bookView).get().name)
    }

    @Test
    fun cachePutMethodReturnValueIsCacheData() {
        // Given
        val bookView = BookView(id = nextUUID(), name = "JPA_start")

        // When
        shipperViewRepositoryImpl.saveWithCachePutWithReturn(bookView)

        // Then
        assertTrue(isCacheExistById(bookView))
        assertEquals(bookView.id, getCacheById(bookView).get().id)
        assertEquals(bookView.name, getCacheById(bookView).get().name)
    }

    // Check cache key rule
    @Test
    fun cacheableMethodParameterIsDefaultCacheKey() {
        // Given
        val bookView = BookView(id = nextUUID(), name = "Algorithm")

        // When
        shipperViewRepositoryImpl.saveWithCacheableDefaultKey(bookView)

        // Then
        assertFalse(isCacheExistById(bookView.id))
        assertTrue(isCacheExistById(bookView))
    }

    @Test
    fun cachePutMethodParameterIsDefaultCacheKey() {
        // Given
        val bookView = BookView(id = nextUUID(), name = "Practice_vim")

        // When
        shipperViewRepositoryImpl.saveWithCachePutDefaultKey(bookView)

        // Then
        assertFalse(isCacheExistById(bookView.id))
        assertTrue(isCacheExistById(bookView))
    }

    @Test
    fun setCacheableCustomCacheKey() {
        // Given
        val bookView = BookView(id = nextUUID(), name = "OOP")

        // When
        shipperViewRepositoryImpl.saveWithCacheableCustomKey(bookView)

        // Then
        assertTrue(isCacheExistById(bookView.id))
        assertFalse(isCacheExistById(bookView))
    }

    @Test
    fun setCachePutCustomCacheKey() {
        // Given
        val bookView = BookView(id = nextUUID(), name = "Start_elastic_search")

        // When
        shipperViewRepositoryImpl.saveWithCachePutCustomKey(bookView)

        // Then
        assertTrue(isCacheExistById(bookView.id))
        assertFalse(isCacheExistById(bookView))
    }

    // Check cache lifecycle
    @Test
    fun cacheExistsWhenDontEvict() {
        // Given
        val bookView = BookView(id = nextUUID(), name = "Start_MSA")

        // When
        shipperViewRepositoryImpl.saveWithCacheableCustomKey(bookView)
        shipperViewRepositoryImpl.deleteWithoutEvict(bookView)

        val isDeletedBookViewExist = shipperViewRepositoryImpl.isExist(bookView.id)

        // Then
        assertFalse(isDeletedBookViewExist)
        assertTrue(isCacheExistById(bookView.id))
    }

    @Test
    fun deleteCacheWhenCacheEvict() {
        // Given
        val bookView = BookView(id = nextUUID(), name = "Spring_boot")

        // When
        shipperViewRepositoryImpl.saveWithCacheableCustomKey(bookView)
        shipperViewRepositoryImpl.deleteWithEvictCustomKey(bookView)

        val idDeletedBookViewExist = shipperViewRepositoryImpl.isExist(bookView.id)

        // Then
        assertFalse(idDeletedBookViewExist)
        assertFalse(isCacheExistById(bookView.id))
    }
}