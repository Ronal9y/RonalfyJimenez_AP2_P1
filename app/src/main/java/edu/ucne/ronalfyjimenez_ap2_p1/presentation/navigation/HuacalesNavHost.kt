package edu.ucne.ronalfyjimenez_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales.HuacalDeleteScreen
import edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales.HuacalEditScreen
import edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales.HuacalListScreen
import edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales.HuacalScreen

@Composable
fun HuacalesNavHost(
    nav: NavHostController
) {
    NavHost(navController = nav, startDestination = Screen.List) {
        composable<Screen.List> {
            HuacalListScreen(
                onAdd = { nav.navigate(Screen.Register) },
                onEdit = { id -> nav.navigate(Screen.Edit(id)) },
                onDelete = { id -> nav.navigate(Screen.Delete(id)) }
            )
        }

        composable<Screen.Register> {
            HuacalScreen(
                idEntrada = null,
                goBack = { nav.popBackStack() }
            )
        }

        composable<Screen.Edit> {
            val args = it.toRoute<Screen.Edit>()
            HuacalEditScreen(
                idEntrada = args.idEntrada,
                goBack = { nav.popBackStack() }
            )
        }

        composable<Screen.Delete> {
            val args = it.toRoute<Screen.Delete>()
            HuacalDeleteScreen(
                idEntrada = args.idEntrada,
                goBack = { nav.popBackStack() }
            )
        }
    }
}