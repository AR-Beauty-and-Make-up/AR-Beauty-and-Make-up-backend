package ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.repository

import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.builders.ProductBuilder
import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.persistence.ProductRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    lateinit var productRepository : ProductRepository

    @Test
    fun productRepositorySaveAndRetriveProduct(){
        val aProduct = ProductBuilder.aProduct().build()
        productRepository.save(aProduct)

        val retrievedProduct = productRepository.findById(aProduct.id!!).get()

        Assert.assertEquals(aProduct.name(), retrievedProduct.name())

    }

    @Test
    fun whenWeAskForAllProductsProductsRepositoryReturnsAListWithAllProducts(){
        val aProduct = ProductBuilder.aProduct().build()
        val anotherProduct = ProductBuilder.aProduct().withName("Agua Micelar").build()
        productRepository.save(aProduct)
        productRepository.save(anotherProduct)

        val retrievedProductList = productRepository.findAll()
        Assert.assertEquals(2, retrievedProductList.size)
    }

}