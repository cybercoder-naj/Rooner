package di

import controller.Controller
import controller.TestController
import model.RoonerModel

object AppContainer {
    val controller: Controller = TestController()

    val model: RoonerModel = RoonerModel(controller)
}