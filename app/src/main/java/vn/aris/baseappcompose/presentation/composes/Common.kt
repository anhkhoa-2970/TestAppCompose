package vn.aris.baseappcompose.presentation.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import vn.aris.baseappcompose.R
import vn.aris.baseappcompose.domain.models.CommonItemModel
import vn.aris.baseappcompose.presentation.ui.theme.styleTextGray
import vn.aris.baseappcompose.utils.Constants
import vn.aris.baseappcompose.utils.Constants.TIME_DISPLAY_TOAST

@Composable
fun CustomDropDownMenu(
    onItemSelected: (CommonItemModel) -> Unit,
    itemList: List<CommonItemModel>,
    modifier: Modifier = Modifier,
    selectedItem: CommonItemModel,
    dropdownWidth: Dp = 200.dp,
    dropdownHeight: Dp = 32.dp
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(
            modifier = modifier
                .width(dropdownWidth)
                .height(dropdownHeight)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .border(1.dp, colorResource(id = R.color.color_border_and_line), RoundedCornerShape(10.dp))
                .clickable { expanded = true }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = selectedItem.icon), contentDescription = null)
            Text(
                text = selectedItem.name,
                style = styleTextGray,
                modifier = modifier
                    .padding(8.dp)
                    .weight(1f)
                    .wrapContentHeight(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null
            )
        }


        DropdownMenu(
            modifier = modifier
                .width(dropdownWidth)
                .background(Color.White)
                .padding(vertical = 0.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            itemList.forEach { item ->
                DropdownMenuItem(
                    modifier = modifier.height(25.dp),
                    onClick = {
                        expanded = false
                        onItemSelected(item)
                    },
                    text = {
                        val isSelected = item == selectedItem
                        val textColor = if (isSelected) {
                            colorResource(id = R.color.color_main)
                        } else {
                            Color.Black
                        }
                        Text(text = item.name, color = textColor)
                    },
                    leadingIcon = { Image(painter = painterResource(id = item.icon), contentDescription = null) },
                )
            }
        }
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    name: String = "",
    paddingStart: Dp = 8.dp,
    textAlign: TextAlign = TextAlign.Center,
    textColor: Color = Color.White,
    bgColor: Color = colorResource(id = R.color.color_main),
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, colorResource(id = R.color.color_main)),
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                leadingIcon()
            }

            if (textAlign == TextAlign.Start) {
                Spacer(modifier = Modifier.width(paddingStart))
            }
            Text(
                text = name,
                modifier = Modifier.weight(1f),
                textAlign = textAlign,
                color = textColor
            )

            trailingIcon?.let {
                trailingIcon()
            }
        }
    }
}

@Composable
fun CustomNumericTextField(
    modifier: Modifier = Modifier,
    textValue: String,
    placeHolder: String = "",
    maxLength: Int = 255,
    iconError: Painter? = null,
    isError: Boolean = false,
    textValueChanged: (String) -> Unit
) {
    val borderColor = if (isError) Color.Red else colorResource(id = R.color.color_border_and_line)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.border(
            border = BorderStroke(1.dp, borderColor),
            shape = RoundedCornerShape(10.dp)
        ).padding(start = 16.dp, end = 10.dp).fillMaxWidth()
    ) {
        BasicTextField(
            value = textValue,
            onValueChange = { newInput ->
                val newPhoneNumber = newInput.filter { it.isDigit() }
                if (newPhoneNumber.length <= maxLength) {
                    textValueChanged(newPhoneNumber)
                }
            },
            singleLine = true,
            modifier = modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (textValue.isEmpty()) {
                        Text(
                            text = placeHolder,
                            color = colorResource(id = R.color.color_text_hint),
                            modifier = modifier
                                .wrapContentHeight()
                                .align(Alignment.CenterStart)
                        )
                    }
                    innerTextField()
                }
            }
        )
        if (isError && iconError != null) {
            Icon(
                painter = iconError,
                contentDescription = null,
                tint = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomNumericTextFieldPreview() {
    CustomNumericTextField(
        textValue = "",
        textValueChanged = {},
        placeHolder = stringResource(id = R.string.txt_phone_number_hint),
        iconError = painterResource(id = R.drawable.ic_input_error),
        isError = true
    )
}

@Preview(showBackground = true)
@Composable
fun CustomDropDownMenuPreview() {
    CustomDropDownMenu(
        onItemSelected = {},
        itemList = CommonItemModel.getLanguageList(),
        selectedItem = CommonItemModel.getLanguageList()[0]
    )
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview(
) {
    CustomButton(
        name = "button name",
        leadingIcon = {
            Image(painter = painterResource(id = R.drawable.flag_viet_nam), contentDescription = null)
        },
        trailingIcon = {
            Image(painter = painterResource(id = R.drawable.flag_india), contentDescription = null)
        },
        onClick = {}
    )
}

/**
 * Composable that displays formatted [price] that will be formatted and displayed on screen
 */
@Composable
fun FormattedPriceLabel(subtotal: String, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.subtotal_price, subtotal),
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun ShowProgressDialog() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = colorResource(id = R.color.color_main))
    }
}

@Composable
fun CustomToast(
    message: String,
    modifier: Modifier = Modifier,
    messageType: Constants.MessageType = Constants.MessageType.ERROR,
    callBack: () -> Unit
) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(isVisible) {
        delay(TIME_DISPLAY_TOAST)
        isVisible = false
        callBack()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = if (messageType == Constants.MessageType.ERROR) {
                        colorResource(id = R.color.color_red)
                    } else {
                        colorResource(id = R.color.color_toast_success)
                    },
                    shape = RoundedCornerShape(4.dp)
                ),
        ) {
            Row(
                modifier = modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (messageType == Constants.MessageType.SUCCESS) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_toast_success),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                Text(
                    text = message,
                    color = Color(0xFFF8FEFF),
                    fontSize = 14.sp,
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomToastPreview() {
    //CustomToast(message = "1234567890123456789012345678901234567891234"){}
    CustomToast(message = "123456789012345678901234567890123456789", messageType = Constants.MessageType.SUCCESS){}
}