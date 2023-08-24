

//Adding active class to the product type
function addActiveClass(ptype)
{
    console.log(ptype)
    if(ptype=='N')
    {
        document.getElementById("type1").style.borderBottom='2px solid black';
    }

    if(ptype=='E')
    {
        document.getElementById("type2").style.borderBottom='2px solid black';
    }

    if(ptype=='B')
    {
        document.getElementById("type3").style.borderBottom='2px solid black';
    }

    if(ptype=='R')
    {
        document.getElementById("type4").style.borderBottom='2px solid black';
    }
}

var ptype=document.getElementById("here").innerHTML;
addActiveClass(ptype);
