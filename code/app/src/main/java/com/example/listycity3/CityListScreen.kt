package com.example.listycity3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onEditCity: (City?,City) -> Unit,
    onRemoveCity: (City?) -> Unit,
    modifier: Modifier = Modifier
) { var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var editCityName by remember { mutableStateOf("") }
    var editProvinceName by remember { mutableStateOf("") }
    var showEditCityFields by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf<City?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showAddCityFields = !showAddCityFields
                }
            ) {
                Text("add")
            }

            FloatingActionButton(
                    modifier = Modifier.padding(16.dp),
            onClick = {
                if (selectedCity is City) {
                    onRemoveCity(
                        selectedCity
                    )
                    selectedCity=null
                }
            }
            ) {
            Text("remove")
            }

            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showEditCityFields = !showEditCityFields
                }
            ) {
                Text("edit")
            }
        }

        if (showAddCityFields) {
            Column(modifier= Modifier){
                Row(modifier = Modifier){
                    Text(text="Add a City",
                        modifier = Modifier.padding(16.dp,0.dp),
                        fontSize = 15.sp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp,0.dp)
                ) {
                    Row(modifier = Modifier){}
                    OutlinedTextField(
                        value = newCityName,
                        onValueChange = { newCityName = it },
                        label = { Text("City") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = newProvinceName,
                        onValueChange = { newProvinceName = it },
                        label = { Text("Province") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        modifier = Modifier.padding(vertical = 12.dp),
                        onClick = {
                            if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                                onAddCity(
                                    City(
                                        name = newCityName,
                                        province = newProvinceName
                                    )
                                )
                                newCityName = ""
                                newProvinceName = ""
                                showAddCityFields = false
                            }
                        }
                    ) {
                        Text("Add City")
                    }


                }

            }

        }

        if (showEditCityFields) {
            Column(modifier= Modifier){
                Row(modifier = Modifier){
                    Text(text="Edit Selected City",
                        modifier = Modifier.padding(16.dp,0.dp),
                        fontSize = 15.sp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp,0.dp)
                ) {

                    OutlinedTextField(
                        value = editCityName,
                        onValueChange = { editCityName = it },
                        label = { Text("City") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = editProvinceName,
                        onValueChange = { editProvinceName = it },
                        label = { Text("Province") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        modifier = Modifier.padding(vertical = 12.dp),
                        onClick = {
                            if (editCityName.isNotBlank() && editProvinceName.isNotBlank() && selectedCity is City) {
                                onEditCity(
                                    selectedCity,
                                    City(
                                        name = editCityName,
                                        province = editProvinceName
                                    )
                                )
                                editCityName = ""
                                editProvinceName = ""
                                showEditCityFields = false
                                selectedCity=null
                            }
                        }
                    ) {
                        Text("Edit City")
                    }

                }



            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                CityRow(city = city, selectedCity = selectedCity, onClick = {
                    selectedCity = if(selectedCity==city){
                        null
                    } else {
                        city
                    }
                })
                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }

}

@Composable
fun CityRow(city: City, selectedCity:City?, onClick: () -> Unit,) {
    var cityRowColor = Color(0xFFFFFFFF)
    if (selectedCity == city){
        cityRowColor  = Color(0xFFBFBFBF)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickable{
                onClick()
            }
            .background(cityRowColor)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = { it },
            onEditCity = { old, edited -> },
            onRemoveCity = { toRemove -> },

            )
    }
}
