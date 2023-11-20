package di

import controller.Controller
import controller.RoonerController
import model.RoonerModel

object AppModule {
    fun provideViewModel(controller: Controller): RoonerModel {
        return RoonerModel(controller)
    }

    fun provideController(): Controller {
        return RoonerController()
    }
}