const formatNumber = (x) => x.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ' ');
const totalPriceWrapper = document.getElementById('total-price');
const ACTION = {
    PLUS: 'plus',
    MINUS: 'minus'
};
const getItemSubTotalPrice = (input) => Number(input.value)*Number(input.dataset.price);
const setTotalPrice = (value) => {
    totalPriceWrapper.textContent = formatNumber(value);
    totalPriceWrapper.dataset.value = value;
};
const init = () => {
    let totalCost = 0;

    [...document.querySelectorAll('.basket_item')].forEach((basketItem) => {
        totalCost += getItemSubTotalPrice(basketItem.querySelector('.input'));
    });
    setTotalPrice(totalCost);
};
const calculateSeparateItem = (basketItem, action) => {
    const input = basketItem.querySelector('.input');

    switch (action) {
        case ACTION.PLUS:
            input.value++;
            setTotalPrice(Number(totalPriceWrapper.dataset.value) + Number(input.dataset.price));
            break;
        case ACTION.MINUS:
            input.value--;
            setTotalPrice(Number(totalPriceWrapper.dataset.value) - Number(input.dataset.price));
            break;
    }
    basketItem.querySelector('.subtotal').textContent = `${formatNumber(getItemSubTotalPrice(input))} грн.`;
};

document.getElementById('basket').addEventListener('click', (event) => {
    if (event.target.classList.contains('btn-minus')) {
        const input = event.target.closest('.basket_item').querySelector('.input');

        if (Number(input.value) !== 1) {
            calculateSeparateItem(
                event.target.closest('.basket_item'),
                ACTION.MINUS
            );
        }
    }

    if (event.target.classList.contains('btn-plus')) {
        calculateSeparateItem(
            event.target.closest('.basket_item'),
            ACTION.PLUS
        );
    }
});

init();
