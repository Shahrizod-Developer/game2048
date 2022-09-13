package uz.xsoft.lesson19.contracts


interface GameContract {

    interface Repository {
        fun getMatrix(): Array<ArrayList<Int>>
        fun moveLeft()
        fun moveRight()
        fun moveUp()
        fun moveDown()
        fun getScore():Int

    }

    interface View {
        fun changeState(matrix: Array<ArrayList<Int>>, score: Int)
    }

    interface Presenter {
        fun startGame()
        fun moveLeft()
        fun moveRight()
        fun moveUp()
        fun moveDown()
    }
}