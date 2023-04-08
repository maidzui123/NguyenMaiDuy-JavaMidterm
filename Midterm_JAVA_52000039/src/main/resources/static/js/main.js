localStorage.setItem("name","")
// Increase/Decrease Quantity Of Sneaker
function increaseCount(a, b) {
    var input = b.previousElementSibling;
    var value = parseInt(input.value, 10);
    value = isNaN(value) ? 0 : value;
    value++;
    input.value = value;
}

function decreaseCount(a, b) {
    var input = b.nextElementSibling;
    var value = parseInt(input.value, 10);
    if (value > 1) {
        value = isNaN(value) ? 0 : value;
        value--;
        input.value = value;
    }
}

// Search Sneakers
const searchInput = document.getElementById("search_input")
const searchResult = document.getElementById("search_result-outer")
function showInfoSneaker(id) {
    fetch('/idSneaker/'+ id)
        .then(res => res.json())
        .then(products => {
            document.getElementById('sneaker_info-outer').setAttribute("sneaker_id", products.id)
            document.getElementById('info_img').setAttribute("src", products.image)
            document.getElementById('info_name').innerHTML = products.name
            document.getElementById('info_brand').innerHTML = products.brand
            document.getElementById('info_gender').innerHTML = products.gender
            document.getElementById('info_price').innerHTML = products.price.toLocaleString() + "₫"
        })
        .catch(err => {
            console.log(err)
        })

}
function showSearchResult(listSneaker) {
    searchResult.innerHTML = ''
    if (listSneaker.length === 0)
    {
        searchResult.insertAdjacentHTML('beforeend',
            `<div class="search_result">
                            <div class="search_result-infor search_result-notfound">
                                <h5>The sneaker you were looking for was not found</h5>
                            </div>
                        </div>`)
    }
    else
    {
        listSneaker.forEach(item => {
            searchResult.insertAdjacentHTML('beforeend',
                `<div class="search_result" data-bs-toggle="modal"
                             data-bs-target="#item_info" onclick="showInfoSneaker(${item.id})">
                                <img src="${item.image}" alt="">
                                <div class="search_result-infor">
                                    <h5>${item.name}</h5>
                                    <p>${item.price.toLocaleString()}₫</p>
                                </div>
                            </div>`)
        })
    }
}


searchInput.addEventListener("input", () => {
    if(searchInput.value.trim() === '')
    {
        searchResult.style.display = 'none'
    }
    else {
        searchResult.style.display = 'flex'
        const sneakerName = searchInput.value
        fetch('/searchSneaker?name=' + sneakerName)
            .then(res => res.json())
            .then(products => {
                showSearchResult(products)
            })
            .catch(err => {
                console.log(err)
            })
    }
})
searchInput.addEventListener("keydown", function(event) {
    if (event.keyCode === 13) {
        localStorage.setItem('name', searchInput.value)
        filterSneaker()
        window.location.href = "http://localhost:8080/#allProducts"
        searchResult.style.display = 'none'
        searchInput.value = ''
    }
});
// Format Price Home Page
function formatHomePrice() {
    const homePrice = document.querySelectorAll('.home_price')
    for(let i = 0; i < homePrice.length; i++) {
        const formatPrice = homePrice[i].innerHTML
        const formattedPrice = Number(formatPrice).toLocaleString()
        homePrice[i].innerHTML = formattedPrice + "₫"
    }
}
formatHomePrice()

// Filter Sneaker with brand, price, gender, sort
function showHomePageSneaker(listSneaker) {
    const allSneakerNew = document.getElementById('all_product-filter')
    resetHomePageSneaker()
    listSneaker.forEach(item => {
        allSneakerNew.insertAdjacentHTML('beforeend',
            `<div id="${item.id}" class="card col-3 text-center mb-2 sneaker_outer" data-aos="fade-up" data-bs-toggle="modal"
                             data-bs-target="#item_info" onclick="showInfoSneaker(${item.id})">
                            <img src="${item.image}" class="card-img-top">
                            <div class="card-body">
                                <h5 class="card-title">${item.name}</h5>
                                <p class="card-text home_price">${item.price}</p>
                            </div>
                        </div>`)
    })
    formatHomePrice()
}
function resetHomePageSneaker() {
    const allSneakerNew = document.getElementById('all_product-filter')
    while (allSneakerNew.firstChild)
    {
        allSneakerNew.removeChild(allSneakerNew.firstChild)
    }
}

function filterSneaker() {
    const filterPrice = document.getElementById('filter_price')
    const valuePrice = filterPrice.value.split('/')
    const filterBrand = document.getElementById('filter_brand')
    const filterGender = document.getElementById('filter_gender')
    const filterSort = document.getElementById('filter_sort')
    const priceMin = valuePrice[0]
    const priceMax = valuePrice[1]
    let nameSneaker = localStorage.getItem("name")
    if(nameSneaker == null)
    {
        nameSneaker = ""
    }
        fetch('/filterSneaker?price_min='+priceMin+'&price_max='+priceMax+'&brand='+filterBrand.value+'&gender='+filterGender.value+'&sort='+filterSort.value +'&name='+nameSneaker)
            .then(res => res.json())
            .then(products => {
                console.log(priceMin)
                console.log(priceMax)
                showHomePageSneaker(products)
            })
            .catch(err => {
                console.log(err)
            })
    // }
}


// Add New Sneaker Or Old Sneaker To Cart
function addNewSneakerToCart() {
    const size = getSize()
    const sneakerId = document.getElementById('sneaker_info-outer').getAttribute('sneaker_id')
    const quantity = document.getElementById('sneaker_quantity').value
    fetch(`/addNewCart?sneakerId=${sneakerId}&quantity=${quantity}&size=${size}`, {
        method: 'put',
    })
        .then(() => {
            checkCartExist()
        })
        .catch(err => {
            console.log(err)
        })
}
function addOldSneakerToCart(cartId) {
    const size = getSize()
    const sneakerId = document.getElementById('sneaker_info-outer').getAttribute('sneaker_id')
    const quantity = document.getElementById('sneaker_quantity').value
    fetch(`/addOldCart?sneakerId=${sneakerId}&quantity=${quantity}&size=${size}&orderId=${cartId}`, {
        method: 'put',
    }).then(res => res.json())
        .then(() => {
            checkCartExist()
        })

        .catch(err => {
            console.log(err)
        })
}
function getSize() {
   const size = document.getElementsByName('item_size');
   for(var i = 0; i < size.length; i++)
   {
       if(size[i].checked) {
           return size[i].value
       }
   }
}
function showSneakerInCart(cart) {
    document.getElementById('sneakerInCart').innerHTML = ''
    const listSneaker = cart.data[0].orderSneakerDetails
    console.log(listSneaker)
    listSneaker.forEach(item => {
        document.getElementById('sneakerInCart').insertAdjacentHTML("beforeend",
        `<div class="sneaker_ordered-outer">
                             <div class="sneaker_ordered-body">
                                    <h5>${item.product.name}</h5>
                                    <p>Price: ${item.product.price.toLocaleString()}₫</p>
                                    <p>Size: ${item.size}</p>
                                    <p>Quantity: ${item.quantity}</p>
                             </div>
                  </div>`)}
    )

}

function checkCartExist() {
    fetch(`/getCart?status=0`)
        .then(res => res.json())
        .then(cart => {
            document.getElementById('cart-quantity').innerHTML = cart.count
            const cartInfo = cart.data
            console.log(cartInfo.length)
            if(cartInfo.length == 0 && cart.length == 0)
            {
                window.localStorage.setItem('cartId','0')
            }
            else {
                showSneakerInCart(cart)
                window.localStorage.setItem('cartId',cartInfo[0].id)

                // if (cartInfo[0].orderSneakerDetails.length == 0 )
                // {
                //     window.localStorage.setItem('cartId','0')
                // }
                // else {
                // }
            }

        })
}
checkCartExist()
function addSneakerToCart() {
    const cartId = localStorage.getItem('cartId')
    if (cartId == 0)
    {
        addNewSneakerToCart()
        alert('Add To Cart Success')
    }
    else {
        addOldSneakerToCart(cartId)
        alert('Add To Cart Success')
    }
}

// Show List Purchase History & Purchase Infomation
function fetchHistoryPurchase() {
    fetch(`http://localhost:8080/getCart?status=1`)
        .then(res => res.json())
        .then(history => {
            // console.log(history)
            showHistoryPurchase(history)
        })
        .catch(err => {
            console.log(err)
        })
}
fetchHistoryPurchase()
function showHistoryPurchase(history) {
    document.getElementById('history_outer').innerHTML = ''
    let listHistory = history.data
    listHistory.forEach(item => {
        let total = item.price.toLocaleString()
        document.getElementById('history_outer').insertAdjacentHTML("beforeend",
            `<li class="purchase_history" data-bs-toggle="modal" data-bs-target="#modal_history" onclick="fetchHistoryInfo(${item.id})">
                                <h6>Purchase Id: ${item.id}</h6>
                                <p>Time Created: ${item.createdAt}</p>
                                <p>Total: ${total}₫</p>
                            </li>`)
    })
}
function fetchHistoryInfo(id) {
    fetch(`http://localhost:8080/getCart?status=1`)
        .then(res => res.json())
        .then(history => {
            checkIdHistory(history, id)
        })
        .catch(err => {
            console.log(err)
        })
}
function checkIdHistory(history, id) {
    let list = history.data
    list.forEach(item => {
        if (item.id == id)
        {
            showHistoryInfo(item)
        }
    })
}
function showHistoryInfo(data) {
    document.getElementById('modal_history-body').innerHTML = ''
    let listHistory = data.orderSneakerDetails
    listHistory.forEach(item => {
        document.getElementById('modal_history-body').insertAdjacentHTML("beforeend",
            `<div class="sneaker_ordered-outer" style="border: 1px solid #000000 !important;">
                       <div class="sneaker_ordered-body" style="color: #000000 !important;">
                               <h5>${item.product.name}</h5>
                               <p>Price: ${item.product.price.toLocaleString()}₫</p>
                               <p>Size: ${item.size}</p>
                               <p>Quantity: ${item.quantity}</p>
                       </div>
                  </div>`)
    })
}


