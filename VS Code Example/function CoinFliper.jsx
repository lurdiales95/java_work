function CoinFliper() {
    console.log("rendering component");
    let message = "Click the button to flip a coin!";

    function generateRandomNumber() {
        return Math.floor(Math.random() * 2);
    }

    function flipCoin() {
        console.log("flipping coin");
        const randomNum = generateRandomNumber();
        message = randomNum == 0 ? 'Heads' : 'Tails';
    }
    return (
        <div> 
            <h1>Coin Flip</h1>
            <button onClick={flipCoin}>Flip Coin</button>
            <p>{message}</p>
        </div>
    )

}
