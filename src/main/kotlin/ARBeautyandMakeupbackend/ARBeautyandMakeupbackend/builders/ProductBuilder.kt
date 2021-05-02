package ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.builders

import ARBeautyandMakeupbackend.ARBeautyandMakeupbackend.model.product.Product

class ProductBuilder {

    private var id:Long? = null
    private var name:String = "Crema desmasquillante"
    private var price:Double = 100.00
    private var category:String = "Cremas"
    private var photo:String = ""


    companion object {
        fun aProduct(): ProductBuilder {
            return ProductBuilder()
        }

    }

    fun build() : Product {
        return Product(this.name, this.price, this.category, this.photo)
    }

    fun withName(aName: String): ProductBuilder {
        this.name = aName
        return this
    }

    fun withPrice(aPrice: Double): ProductBuilder {
        this.price = price
        return this
    }

    fun withCategory(aCategory: String): ProductBuilder {
        this.category = aCategory
        return this
    }

    fun withPhoto(aPhoto: String): ProductBuilder {
        this.photo = aPhoto
        return this
    }

}
