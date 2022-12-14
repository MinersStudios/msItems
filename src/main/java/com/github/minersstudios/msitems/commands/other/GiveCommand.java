package com.github.minersstudios.msitems.commands.other;

import com.github.minersstudios.msitems.items.CustomItem;
import com.github.minersstudios.msitems.items.RenameableItem;
import com.github.minersstudios.msitems.utils.ChatUtils;
import com.github.minersstudios.msitems.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class GiveCommand {

	public static boolean runCommand(@NotNull CommandSender sender, String @NotNull ... args) {
		if (args.length < 3) return false;
		if (args[1].length() > 2) {
			Player player = Bukkit.getPlayer(args[1]);

			int amount = args.length == 4 && args[3].matches("[0-99]+")
					? Integer.parseInt(args[3])
					: 1;
			if (player == null) {
				return ChatUtils.sendError(sender, Component.text("Данный игрок не на сервере!"));
			}
			ItemStack itemStack;
			RenameableItem renameableItem = ItemUtils.RENAMEABLE_ITEMS.get(args[2]);
			CustomItem customItem = ItemUtils.CUSTOM_ITEMS.get(args[2]);
			if (customItem == null) {
				if (renameableItem == null) {
					return ChatUtils.sendError(sender, Component.text("Такого блока не существует!"));
				} else {
					itemStack = renameableItem.getResultItemStack();
				}
			} else {
				itemStack = customItem.getItemStack();
			}
			itemStack.setAmount(amount);
			player.getInventory().addItem(itemStack);
			return ChatUtils.sendInfo(sender, Component.text("Выдано " + amount + " " + ChatUtils.convertPlainComponentToString(Objects.requireNonNull(itemStack.displayName())) + " Игроку : " + player.getName()));
		}
		return ChatUtils.sendWarning(sender, Component.text("Ник не может состоять менее чем из 3 символов!"));
	}
}
