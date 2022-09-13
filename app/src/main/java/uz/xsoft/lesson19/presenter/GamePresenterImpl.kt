package uz.xsoft.lesson19.presenter

import uz.xsoft.lesson19.contracts.GameContract

class GamePresenterImpl(
    private val view: GameContract.View,
    private val repository: GameContract.Repository
) : GameContract.Presenter {
    override fun startGame() {
        view.changeState(repository.getMatrix(), repository.getScore())
    }

    override fun moveLeft() {
        repository.moveLeft()
        view.changeState(repository.getMatrix(), repository.getScore())
    }

    override fun moveRight() {
        repository.moveRight()
        view.changeState(repository.getMatrix(), repository.getScore())
    }

    override fun moveUp() {
        repository.moveUp()
        view.changeState(repository.getMatrix(), repository.getScore())
    }

    override fun moveDown() {
        repository.moveDown()
        view.changeState(repository.getMatrix(), repository.getScore())
    }
}