package di

import controller.Controller
import controller.RoonerController
import controller.TestController
import model.RoonerModel

object AppModule {
    fun provideViewModel(controller: Controller): RoonerModel {
        return RoonerModel(controller)
    }

    fun provideController(): Controller {
        return TestController()
    }
}