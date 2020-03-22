package com.example.furnitureapp.models

class Product{
    var id: String? = null
    var name : String? = null
    var size : String? = null
    var code : String? = null
    var price : Float = 0.0f
    var image:Int
    var material: String? = null
    var available: Int

    constructor(id:String,name:String, size:String, code:String, price:Float, image:Int, material: String, available: Int){
        this.id = id
        this.name = name
        this.size = size
        this.code = code
        this.price = price
        this.image = image
        this.material = material
        this.available = available
    }
}