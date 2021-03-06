# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-12-26 13:24
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('components', '0018_auto_20171222_1831'),
    ]

    operations = [
        migrations.AlterField(
            model_name='checkboxitem',
            name='checkbox',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='items', to='components.CheckboxComponent'),
        ),
        migrations.AlterField(
            model_name='component',
            name='checkbox',
            field=models.OneToOneField(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='component', to='components.CheckboxComponent'),
        ),
    ]
